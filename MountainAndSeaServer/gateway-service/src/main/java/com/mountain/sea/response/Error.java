package com.mountain.sea.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String code;
    private String message;

    public Error(ExceptionEnum exceptionInfoEnum) {
        this.code = exceptionInfoEnum.getErrorCode();
        this.message = exceptionInfoEnum.getErrorMessage();
    }

    public Error(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
