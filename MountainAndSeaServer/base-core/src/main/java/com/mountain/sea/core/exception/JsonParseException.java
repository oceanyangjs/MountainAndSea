package com.mountain.sea.core.exception;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 14:06
 */
public class JsonParseException extends Exception {
    private String errorMessage;

    public JsonParseException(String errorMessage) {
        super(errorMessage);
    }
}
