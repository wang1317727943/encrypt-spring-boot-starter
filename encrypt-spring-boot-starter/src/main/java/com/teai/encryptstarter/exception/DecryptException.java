package com.teai.encryptstarter.exception;

/**
 * @ClassName
 * @description
 * @Author wangll
 * @Date 2020/9/26 14:44
 * @Version 1.0
 **/
public class DecryptException extends RuntimeException {
    private String message;

    public DecryptException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
