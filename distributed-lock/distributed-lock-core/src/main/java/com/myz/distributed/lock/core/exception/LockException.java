/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.exception;

/**
 * @author maoyz0621 on 2022/5/14
 * @version v1.0
 */
public class LockException extends RuntimeException {

    public LockException() {
        super();
    }

    public LockException(String message) {
        super(message);
    }
}