/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.config;

/**
 * @author maoyz0621 on 2022/5/22
 * @version v1.0
 */
public enum LockType {
    /**
     * 可重入锁
     */
    Reentrant,
    /**
     * 公平锁
     */
    Fair,
    /**
     * 读锁
     */
    Read,
    /**
     * 写锁
     */
    Write;

    LockType() {
    }
}