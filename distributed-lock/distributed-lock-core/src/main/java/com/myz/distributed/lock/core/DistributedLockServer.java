/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core;

import com.myz.distributed.lock.core.config.DistributedLockProperties;
import com.myz.distributed.lock.core.exception.LockException;
import com.myz.distributed.lock.core.executor.DistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class DistributedLockServer implements InitializingBean {

    private Map<Class<? extends DistributedLockExecutor>, DistributedLockExecutor> distributedLockExecutorMap = new ConcurrentHashMap<>();
    private final List<DistributedLockExecutor> distributedLockExecutors;

    private final DistributedLockProperties distributedLockProperties;

    private DistributedLockExecutor primaryExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(distributedLockExecutors)) {
            log.warn("DistributedLockServer has no DistributedLockExecutor");
            return;
        }
        distributedLockExecutorMap = distributedLockExecutors.stream()
                .collect(Collectors.toConcurrentMap(DistributedLockExecutor::getClass, Function.identity(), (k1, k2) -> k2));
        // for (DistributedLockExecutor distributedLockExecutor : distributedLockExecutors) {
        //     distributedLockExecutorMap.put(distributedLockExecutor.getClass(), distributedLockExecutor);
        // }

        final Class<? extends DistributedLockExecutor> primaryExecutorClass = distributedLockProperties.getPrimaryExecutor();
        if (primaryExecutorClass == null) {
            this.primaryExecutor = distributedLockExecutors.get(0);
        } else {
            this.primaryExecutor = distributedLockExecutorMap.get(primaryExecutorClass);
            Assert.notNull(this.primaryExecutor, "primaryExecutor must be not null");
        }

    }

    public DistributedLockInfo acquire(String lockKey) {
        return acquire(lockKey, 0, -1);
    }

    public DistributedLockInfo acquire(String lockKey, long expire, long acquireTimeout) {
        return acquire(lockKey, expire, acquireTimeout, null);
    }

    /**
     * 加锁
     *
     * @param lockKey        锁key
     * @param expire         锁有效时间
     * @param acquireTimeout 获取锁超时时间
     * @return 锁信息
     */
    public DistributedLockInfo acquire(String lockKey, long expire, long acquireTimeout, Class<? extends DistributedLockExecutor> distributedLockExecutor) {
        DistributedLockExecutor executor = null;
        if (distributedLockExecutor == null || distributedLockExecutor == DistributedLockExecutor.class) {
            executor = this.primaryExecutor;
        } else {
            executor = distributedLockExecutorMap.get(distributedLockExecutor);
        }

        if (executor == null) {
            return null;
        }
        // 锁过期时间
        acquireTimeout = acquireTimeout < 0 ? distributedLockProperties.getAcquireTimeout() : acquireTimeout;
        // 锁有效期
        expire = (!executor.renewal() && expire <= 0) ? distributedLockProperties.getExpire() : expire;
        // 获取锁次数
        int acquireCount = 0;
        String lockVal = "1";
        long start = System.currentTimeMillis();

        try {
            do {
                acquireCount++;
                lockKey += "-" + Thread.currentThread().getId();
                Object lockInstance = executor.acquire(lockKey, lockVal, expire, acquireTimeout);
                if (lockInstance != null) {
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
        } catch (InterruptedException e) {
            throw new LockException();
        }

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