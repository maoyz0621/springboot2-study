/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2error.common;

/**
 * @author maoyz0621 on 2022/8/19
 * @version v1.0
 */
public class BizException extends RuntimeException {

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}