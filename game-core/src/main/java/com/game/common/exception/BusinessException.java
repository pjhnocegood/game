package com.game.common.exception;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    private String status;
    private Object data;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.message = message;
        this.data = data;
    }

    public BusinessException(String statusCode, String message, Object data) {
        super(message);
        this.status = statusCode;
        this.message = message;
        this.data = data;
    }

    public BusinessException(String statusCode, String message, Throwable cause) {
        super(message, cause);
        this.status = statusCode;
        this.message = message;
    }

    public BusinessException(String statusCode, String message, Object data, Throwable cause) {
        super(message, cause);
        this.status = statusCode;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
