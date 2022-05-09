/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.executor;

import com.myz.distributed.lock.core.executor.AbstractDistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * @author maoyz0621 on 2022/5/9
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ZookeeperDistributedLockExecutor extends AbstractDistributedLockExecutor<InterProcessMutex> {

    private final CuratorFramework curatorFramework;

    @Override
    public InterProcessMutex acquire(String lockKey, String lockVal, long expire, long acquireTimeout) {
        return null;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockVal, InterProcessMutex lockInstance) {
        return false;
    }
}