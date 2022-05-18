/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core;

import com.myz.distributed.lock.core.executor.DistributedLockExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DistributedLockInfo<T> {

    /**
     * 锁名称
     */
    private String lockKey;

    /**
     * 锁值
     */
    private String lockValue;

    /**
     * 过期时间
     */
    private Long expire;

    /**
     * 获取锁超时时间
     */
    private Long acquireTimeout;

    /**
     * 获取锁次数
     */
    private int acquireCount;

    /**
     * 锁实例
     */
    private T lockInstance;

    /**
     * 锁执行器
     */
    private DistributedLockExecutor<T> distributedLockExecutor;
}