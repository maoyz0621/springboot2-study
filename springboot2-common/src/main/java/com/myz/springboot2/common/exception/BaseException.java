/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-28 17:54  Inc. All rights reserved.
 */
package com.myz.springboot2.common.exception;

import com.myz.springboot2.common.data.IErrorCodeEnum;

/**
 * @author maoyz
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private IErrorCodeEnum errorCodeI;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCodeEnum getErrorCodeI() {
        return errorCodeI;
    }

    public void setErrorCodeI(IErrorCodeEnum errorCodeI) {
        this.errorCodeI = errorCodeI;
    }
}