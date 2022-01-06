/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.utils;

/**
 * @author maoyz0621 on 2020/11/9
 * @version v1.0
 */
public class UncheckedException extends RuntimeException {

    private static final long serialVersionUID = -6423343433070702016L;

    public UncheckedException(Throwable wrapped) {
        super(wrapped);
    }

    @Override
    public String getMessage() {
        return super.getCause().getMessage();
    }
}