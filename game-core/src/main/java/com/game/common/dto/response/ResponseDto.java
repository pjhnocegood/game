package com.game.common.dto.response;

import com.game.common.constant.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final ResponseStatus status;
    private final String message;
    private final T data;
}
