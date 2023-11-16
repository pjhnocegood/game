package com.game.queue.airdrop.service;

import com.game.common.constant.ErrorMessageConstant;
import com.game.common.dto.request.AirdropRequestMessage;
import com.game.common.entity.db.Airdrop;
import com.game.common.entity.db.AirdropRequestMember;
import com.game.common.entity.db.Member;
import com.game.common.exception.BusinessException;
import com.game.common.repository.db.AirdropRepository;
import com.game.common.repository.db.AirdropRequestMemberRepository;
import com.game.common.repository.db.MemberRepository;
import com.game.common.repository.redis.AirdropRequestRedisRepository;
import com.game.queue.airdrop.dto.AirdropRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AirdropQueueService {

    private final AirdropRequestRedisRepository airdropRequestRedisRepository;

    private final AirdropRepository airdropRepository;

    private final AirdropRequestMemberRepository airdropRequestMemberRepository;

    private final MemberRepository memberRepository;

    private static final Logger log = LoggerFactory.getLogger(AirdropQueueService.class);


    public void requestAirdrop( AirdropRequestMessage airdropRequestMessage) {

        Long memberId =airdropRequestMessage.getMemberId();
        Long airdropId = airdropRequestMessage.getAirdropId();

        Airdrop airdrop = airdropRepository.findById(airdropId).orElseThrow(() -> new BusinessException(ErrorMessageConstant.INVALID_AIRDROP_ID));
        incrementAndValidateAirdropCount(airdropRequestMessage,airdrop);

        AirdropRequestMember airdropRequestMember = AirdropRequestMember.builder()
                .airdrop(airdrop)
                .member(Member.builder().memberId(memberId).build())
                .airdropRequestKey(memberId+"_"+airdropId)
                .build();

        airdropRequestMemberRepository.save(airdropRequestMember);
    }

    private void incrementAndValidateAirdropCount(AirdropRequestMessage airdropRequestMessage,Airdrop airdrop) {

        Long airdropCount = airdropRequestRedisRepository.incrementAirdropCount(airdropRequestMessage.getAirdropId(),airdrop.getCountPerMember());

        if (airdropCount > airdrop.getMaxCount()){
            throw new BusinessException(ErrorMessageConstant.EXCEED_AIRDROP_COUNT);
        }

        Long requestedUser = airdropRequestRedisRepository.incrementMemberRequest(airdropRequestMessage.getAirdropId(),airdropRequestMessage.getMemberId());

        if (requestedUser > 1){
            throw new BusinessException(ErrorMessageConstant.ALREADY_REQUESTED_AIRDROP);
        }

    }



}
