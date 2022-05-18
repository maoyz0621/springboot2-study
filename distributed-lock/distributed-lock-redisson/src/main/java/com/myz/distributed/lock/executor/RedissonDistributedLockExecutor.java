/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.executor;

import com.myz.distributed.lock.core.executor.AbstractDistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/5/9
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class RedissonDistributedLockExecutor extends AbstractDistributedLockExecutor<RLock> {

    private final RedissonClient redissonClient;

    @Override
    public boolean renewal() {
        return true;
    }

    @Override
    public RLock acquire(String lockKey, String lockVal, long expire, long acquireTimeout) {
        try {
            final RLock lock = redissonClient.getLock(lockKey);
            final boolean locked = lock.tryLock(acquireTimeout, expire, TimeUnit.MILLISECONDS);
            return obtainLockInstance(locked, lock);
        } catch (InterruptedException e) {
            log.warn("RedissonDistributedLockExecutor acquire error:", e);
        }
        return null;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockVal, RLock lockInstance) {
        // 当前锁是否被当前线程获取
        if (lockInstance.isHeldByCurrentThread()) {
            try {
                return lockInstance.forceUnlockAsync().get();
            } catch (InterruptedException | ExecutionException e) {
                log.warn("RedissonDistributedLockExecutor releaseLock error:", e);
                return false;
            }
        }
        return false;
    }
}