/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core;

import com.myz.distributed.lock.core.config.DistributedLockProperties;
import com.myz.distributed.lock.core.exception.LockException;
import com.myz.distributed.lock.core.executor.DistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class DistributedLockServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockServer.class);
    private final DistributeLockFactory lockFactory;
    private final DistributedLockProperties distributedLockProperties;

    public DistributedLockInfo acquire(String lockKey) {
        return acquire(lockKey, 0, -1);
    }

    public DistributedLockInfo acquire(String lockKey, long expire, long acquireTimeout) {
        return acquire(lockKey, expire, acquireTimeout, null);
    }

    public DistributedLockInfo acquire(String lockKey, long expire, long acquireTimeout, Class<? extends DistributedLockExecutor> distributedLockExecutor) {
        return acquire(lockKey, expire, acquireTimeout, null, false);
    }

    public DistributedLockInfo acquire(String lockKey, long expire, long acquireTimeout, boolean reentrant) {
        return acquire(lockKey, expire, acquireTimeout, null, reentrant);
    }

    /**
     * 加锁
     *
     * @param lockKey        锁key
     * @param expire         锁有效时间
     * @param acquireTimeout 获取锁超时时间
     * @param reentrant      是否可重入
     * @return 锁信息
     */
    public DistributedLockInfo acquire(String lockKey, long expire, long acquireTimeout, Class<? extends DistributedLockExecutor> distributedLockExecutor, boolean reentrant) {
        DistributedLockExecutor executor = lockFactory.getExecutor(distributedLockExecutor);
        if (executor == null) {
            return null;
        }
        // 锁过期时间
        acquireTimeout = acquireTimeout < 0 ? distributedLockProperties.getAcquireTimeout() : acquireTimeout;
        // 锁有效期
        expire = (!executor.renewal() && expire <= 0) ? distributedLockProperties.getExpire() : expire;
        // 获取锁次数
        int acquireCount = 0;
        String lockVal = UUID.randomUUID().toString();
        long start = System.currentTimeMillis();
        // todo 可重入key 完善 ，instanceId ...
        if (reentrant) {
            lockKey += "-" + Thread.currentThread().getId();
        }
        try {
            do {
                acquireCount++;
                Object lockInstance = executor.acquire(lockKey, lockVal, expire, acquireTimeout);
                if (lockInstance != null) {
                    LOGGER.info("====================== DistributedLockServer acquire success,key=[{}],acquireCount={} =========================", lockKey, acquireCount);
                    return new DistributedLockInfo()
                            .setLockKey(lockKey)
                            .setLockValue(lockVal)
                            .setExpire(expire)
                            .setLockInstance(lockInstance)
                            .setDistributedLockExecutor(executor)
                            .setAcquireCount(acquireCount)
                            .setAcquireTimeout(acquireTimeout)
                            ;
                }
                // 间隔时间
                TimeUnit.MILLISECONDS.sleep(distributedLockProperties.getRetryInterval());
            } while ((System.currentTimeMillis() - start) < acquireTimeout);
        } catch (Exception e) {
            throw new LockException("acquire error");
        }
        LOGGER.warn("====================== DistributedLockServer acquire fail,key=[{}],acquireCount={} =========================", lockKey, acquireCount);
        return null;
    }

    /**
     * 释放锁
     *
     * @return 是否是否锁
     */
    public boolean releaseLock(DistributedLockInfo lockInfo) {
        if (lockInfo == null || lockInfo.getLockInstance() == null) {
            return false;
        }

        return lockInfo.getDistributedLockExecutor().releaseLock(lockInfo.getLockKey(), lockInfo.getLockValue(), lockInfo.getLockInstance());
    }

}