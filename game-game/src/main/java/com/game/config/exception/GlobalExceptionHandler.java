package com.game.config.exception;


import com.game.common.constant.ErrorMessageConstant;
import com.game.common.dto.response.ResponseDto;
import com.game.common.exception.BusinessException;
import com.game.common.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * BusinessException에서 발생한 에러
     *
     * @param ex BusinessException
     * @return ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto>  handleException(
            BusinessException businessException) {

        String message = ErrorMessageConstant.BAD_REQUEST;
        Object data = businessException.getData();

        if (businessException.getMessage() != null)
            message = businessException.getMessage();

        return ResponseUtil.FAIL_RESPONSE(message, data);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseDto>  handleException(
            HttpMessageNotReadableException httpMessageNotReadableException) {

        String message = ErrorMessageConstant.BAD_REQUEST;

        if (httpMessageNotReadableException.getMessage() != null){
            message = httpMessageNotReadableException.getMessage();
        }

        return ResponseUtil.FAIL_RESPONSE(message,null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto> handleException(
            MethodArgumentNotValidException methodArgumentNotValidException) {

         String message = methodArgumentNotValidException.getMessage();

        return ResponseUtil.FAIL_RESPONSE(message,null);
    }






}