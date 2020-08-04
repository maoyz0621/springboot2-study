/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.common.exception;

/**
 * 业务逻辑错误
 * @author maoyz0621 on 20-4-22
 * @version v1.0
 */
public class BusinessException extends RuntimeException {

    private int code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
