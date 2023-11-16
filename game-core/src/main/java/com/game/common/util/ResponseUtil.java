package com.game.common.util;


import com.game.common.constant.ResponseStatus;
import com.game.common.dto.response.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T>ResponseDto SUCCESS (String message, T data) {

        return new ResponseDto(ResponseStatus.SUCCESS, message, data);
    }

    public static <T>ResponseDto FAIL (String message, T data) {
        return new ResponseDto(ResponseStatus.FAIL, message, data);
    }

    public static <T>ResponseDto ERROR (String message, T data) {
        return new ResponseDto(ResponseStatus.ERROR, message, data);
    }

    public static <T>ResponseEntity<ResponseDto> SUCCESS_RESPONSE (String message, T data) {

        return ResponseEntity.ok(SUCCESS(message, data));
    }

    public static <T>ResponseEntity<ResponseDto> SUCCESS_RESPONSE (String message, T data,HttpHeaders httpHeaders) {

        return ResponseEntity.ok().headers(httpHeaders).body(SUCCESS(message, data));
    }

    public static <T>ResponseEntity<ResponseDto> FAIL_RESPONSE (String message, T data) {
        HttpHeaders httpHeaders = new HttpHeaders();
        return ResponseEntity.ok(FAIL(message, data));
    }

    public static <T>ResponseEntity<ResponseDto> ERROR_RESPONSE (String message, T data) {
        return ResponseEntity.ok(ERROR(message, data));
    }
}
