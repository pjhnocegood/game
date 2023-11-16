package com.game.common.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AirdropRequestMessage {

    private Long airdropId;

    private Long memberId;

}
