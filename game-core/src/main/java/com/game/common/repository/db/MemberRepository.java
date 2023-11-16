package com.game.common.repository.db;

import com.game.common.entity.db.Airdrop;
import com.game.common.entity.db.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findOneWithMemberAuthorityByEmailAddress(String name);

    Optional<Member> findByEmailAddress(String name);
}
