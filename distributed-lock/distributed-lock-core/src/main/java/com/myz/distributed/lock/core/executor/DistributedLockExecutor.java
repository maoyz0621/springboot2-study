/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.executor;

/**
 * @author maoyz0621 on 2022/5/9
 * @version v1.0
 */
public interface DistributedLockExecutor<T> {

    /**
     * 续期，只有redisson支持，且expire=-1 生效
     *
     * @return 是否续期
     */
    default boolean renewal() {
        return false;
    }

    /**
     * 加锁
     *
     * @param lockKey        锁key
     * @param lockVal        锁值
     * @param expire         锁有效时间
     * @param acquireTimeout 获取锁超时时间
     * @return 锁信息
     */
    T acquire(String lockKey, String lockVal, long expire, long acquireTimeout);

    /**
     * 释放锁
     *
     * @param lockKey      锁key
     * @param lockVal      锁值
     * @param lockInstance 锁实例
     * @return 是否是否锁
     */
    boolean releaseLock(String lockKey, String lockVal, T lockInstance);
}