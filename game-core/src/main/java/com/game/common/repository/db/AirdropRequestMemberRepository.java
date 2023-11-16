package com.game.common.repository.db;

import com.game.common.entity.db.AirdropRequestMember;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AirdropRequestMemberRepository extends JpaRepository<AirdropRequestMember,Long> {


    List<AirdropRequestMember> findTop30ByIsCompletedOrderByAirdropRequestMemberId(boolean isCompleted);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update airdrop_request_member arm set arm.isCompleted = ?2 where arm.airdropRequestMemberId in ?1")
    void updateCompletedByAirdropRequestMemberIdList(List<Long> airDropRequestMemberIdList, boolean isCompleted);

}
