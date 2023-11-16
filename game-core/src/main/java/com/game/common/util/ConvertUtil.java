package com.game.common.util;

import java.nio.charset.StandardCharsets;

public class ConvertUtil {

    public static byte[] convertToBytes32(Long input) {
        String inputString = input.toString();
        byte[] result = new byte[32]; // 32 바이트 배열 생성

        // 문자열을 UTF-8 인코딩으로 바이트로 변환
        byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8);

        // 입력 문자열의 바이트를 결과 바이트 배열로 복사
        int length = Math.min(inputBytes.length, 32);
        System.arraycopy(inputBytes, 0, result, 0, length);

        return result;
    }
}
