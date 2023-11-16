package com.game.common.repository.redis;

import com.game.common.constant.CommonMessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AirdropRequestRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Long incrementAirdropCount(Long airdropId,Long count) {
        return redisTemplate.opsForValue().increment(CommonMessageConstant.AIRDROP_COUNT_KEY+"_"+airdropId,count);
    }
    public Long getAirdropSize(Long airdropId){
        return redisTemplate.opsForValue().size(CommonMessageConstant.AIRDROP_COUNT_KEY+"_"+airdropId);
    }

    public Long incrementMemberRequest(Long airdropId, Long memberId){
        return redisTemplate.opsForValue().increment(CommonMessageConstant.MEMBER_AIRDROP_REQUEST_KEY+"_"+airdropId+"_"+memberId);
    }

    public Long getMemberRequestSize(Long airdropId, Long memberId){
        return redisTemplate.opsForValue().size(CommonMessageConstant.MEMBER_AIRDROP_REQUEST_KEY+"_"+airdropId+"_"+memberId);
    }



}
