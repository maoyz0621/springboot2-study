/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.service.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author maoyz0621 on 19-8-30
 * @version: v1.0
 */
public interface IRedisLock extends Lock {

    String getName();


    /**
     * @param waitTime  等待时间
     * @param leaseTime 锁释放时间
     * @param unit      单位
     * @return 获取锁是否成功
     * @throws InterruptedException
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    void lock(long leaseTime, TimeUnit unit) throws InterruptedException;

    boolean isLocked();

}
