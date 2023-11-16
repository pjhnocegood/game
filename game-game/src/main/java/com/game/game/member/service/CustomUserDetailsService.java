package com.game.game.member.service;


import com.game.common.entity.db.Member;
import com.game.common.exception.BusinessException;
import com.game.common.repository.db.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String emailAddress) {

        return memberRepository.findOneWithMemberAuthorityByEmailAddress(emailAddress)
                .map(user -> createUser(emailAddress, user))
                .orElseThrow(() -> new UsernameNotFoundException(emailAddress + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createUser(String emailAddress, Member member) {
        if (!member.isActivated()) {
            throw new BusinessException(emailAddress + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getMemberAuthorityList().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityName()))
                .collect(Collectors.toList());

        return new User(member.getEmailAddress(),
                member.getPassword(),
                grantedAuthorities);
    }
}
