/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.exception;

/**
 * @author maoyz0621 on 2022/5/22
 * @version v1.0
 */
public interface LockFailureHandler {

    /**
     * 处理获取锁超时
     */
    void handleLockTimeOut();

    /**
     * 处理释放锁超时
     */
    void handleReleaseTimeout();
}