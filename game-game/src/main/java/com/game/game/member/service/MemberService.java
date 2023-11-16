package com.game.game.member.service;


import com.game.common.constant.ErrorMessageConstant;
import com.game.common.exception.BusinessException;
import com.game.common.repository.db.MemberRepository;
import com.game.game.member.dto.reponse.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberResponse getMemberProfile(Principal principal) {
        return memberRepository.findOneWithMemberAuthorityByEmailAddress(principal.getName())
                .map(MemberResponse::makeMemberResponse)
                .stream()
                .findFirst()
                .orElseThrow(()-> new BusinessException(ErrorMessageConstant.INVALID_USER_INFO));
    }
}
