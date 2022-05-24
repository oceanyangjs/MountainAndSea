package com.mountain.sea.core.exception;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:35
 */
public class RestApiException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public RestApiException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getErrorMessage());
        this.errorCode = exceptionEnum.getErrorCode();
    }

    public RestApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
