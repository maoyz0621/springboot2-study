/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2error.error;

/**
 * @author maoyz0621 on 20-4-22
 * @version v1.0
 */
public class BusinessException extends RuntimeException {

    private String message;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
