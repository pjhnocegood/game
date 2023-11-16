package com.game.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EccKeyResponse {
    private final String privateKey;
    private final String publicKey;
}
