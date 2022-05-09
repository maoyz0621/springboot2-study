/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.executor;

/**
 * @author maoyz0621 on 2022/5/9
 * @version v1.0
 */
public abstract class AbstractDistributedLockExecutor<T> implements DistributedLockExecutor<T> {

    protected T obtainLockInstance(boolean locked, T lockInstance) {
        return locked ? lockInstance : null;
    }
}