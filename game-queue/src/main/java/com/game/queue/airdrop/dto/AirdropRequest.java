package com.game.queue.airdrop.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


@NoArgsConstructor // 이 어노테이션을 추가합니다.
@Getter
public class AirdropRequest {

    private Long airdropId;

    private Long memberId;
}
