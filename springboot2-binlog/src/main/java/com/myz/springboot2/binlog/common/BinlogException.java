/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.common;

/**
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
public class BinlogException extends RuntimeException {

    private static final long serialVersionUID = -2075929347742978932L;

    public BinlogException() {
    }

    public BinlogException(String message) {
        super(message);
    }

    public BinlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public BinlogException(Throwable cause) {
        super(cause);
    }
}