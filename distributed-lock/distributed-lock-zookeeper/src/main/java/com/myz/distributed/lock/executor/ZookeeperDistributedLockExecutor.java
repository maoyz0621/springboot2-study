/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.executor;

import com.myz.distributed.lock.core.executor.AbstractDistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/5/9
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ZookeeperDistributedLockExecutor extends AbstractDistributedLockExecutor<InterProcessMutex> {

    private final CuratorFramework curatorFramework;

    /**
     * InterProcessMutex
     */
    @Override
    public InterProcessMutex acquire(String lockKey, String lockVal, long expire, long acquireTimeout) {
        if (!CuratorFrameworkState.STARTED.equals(curatorFramework.getState())) {
            return null;
        }
        // todo 提取配置参数
        String nodePath = "/curator/lock/%s";

        try {
            InterProcessMutex processMutex = new InterProcessMutex(curatorFramework, String.format(nodePath, lockKey));
            boolean acquire = processMutex.acquire(acquireTimeout, TimeUnit.MILLISECONDS);
            return obtainLockInstance(acquire, processMutex);
        } catch (Exception e) {
            log.warn("ZookeeperDistributedLockExecutor acquire error:", e);
        }
        return null;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockVal, InterProcessMutex lockInstance) {
        try {
            lockInstance.release();
            return true;
        } catch (Exception e) {
            log.warn("ZookeeperDistributedLockExecutor releaseLock error:", e);
        }
        return false;
    }
}