package com.game.game.nft.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.common.constant.ErrorMessageConstant;
import com.game.common.dto.request.AirdropRequestMessage;
import com.game.common.entity.db.Airdrop;
import com.game.common.entity.db.Member;
import com.game.common.exception.BusinessException;
import com.game.common.repository.db.AirdropRepository;
import com.game.common.repository.db.MemberRepository;
import com.game.common.repository.redis.AirdropRequestRedisRepository;
import com.game.game.nft.dto.AirdropRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AirdropService {

    private final RabbitTemplate rabbitTemplate;

    private final AirdropRequestRedisRepository airdropRequestRedisRepository;

    private final ObjectMapper objectMapper ;

    @Value("${rabbitmq.airdrop.request.exchange}")
    private String  airdropRequestRExchangeName;

    @Value("${rabbitmq.airdrop.request.routerKey}")
    private String  airdropRequestRouterKey;

    private final AirdropRepository airdropRepository;

    private final MemberRepository memberRepository;

    public void requestAirDrop(AirdropRequest airdropRequest, Principal principal) {


        Member member= memberRepository.findByEmailAddress(principal.getName()).orElseThrow(() -> new BusinessException(ErrorMessageConstant.INVALID_USER_INFO));


        AirdropRequestMessage airdropRequestMessage = AirdropRequestMessage.builder().airdropId(airdropRequest.getAirdropId()).memberId(member.getMemberId()).build();

        Airdrop airdrop = airdropRepository.findById(airdropRequestMessage.getAirdropId()).orElseThrow(() -> new BusinessException(ErrorMessageConstant.INVALID_AIRDROP_ID));
        validateAirdropCount(airdropRequestMessage,airdrop.getMaxCount());

        sendAirdropRequestToMQ(airdropRequestMessage);

    }


    public void validateAirdropCount(AirdropRequestMessage airdropRequestMessage,Long maxCount) {

        Long airdropCount = airdropRequestRedisRepository.getAirdropSize(airdropRequestMessage.getAirdropId());

        if (airdropCount >= maxCount){
            throw new BusinessException(ErrorMessageConstant.EXCEED_AIRDROP_COUNT);
        }

        Long requestedUser = airdropRequestRedisRepository.getMemberRequestSize(airdropRequestMessage.getAirdropId(),airdropRequestMessage.getMemberId());

        if (requestedUser >= 1){
            throw new BusinessException(ErrorMessageConstant.ALREADY_REQUESTED_AIRDROP);
        }

    }


    private void sendAirdropRequestToMQ(AirdropRequestMessage airdropRequestMessage) {
        String json ="";
        try {
             json = objectMapper.writeValueAsString(airdropRequestMessage);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorMessageConstant.JSON_PROCESSING_EXCEPTION);
        }
        rabbitTemplate.convertAndSend(airdropRequestRExchangeName,airdropRequestRouterKey, json);
    }

}
