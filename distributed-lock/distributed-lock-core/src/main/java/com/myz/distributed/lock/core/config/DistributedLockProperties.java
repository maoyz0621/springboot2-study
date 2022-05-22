/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.config;

import com.myz.distributed.lock.core.executor.DistributedLockExecutor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Prefix must be in canonical form
 *
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@ConfigurationProperties(prefix = "myz.distributed.lock")
public class DistributedLockProperties {
    /**
     * 过期时间 单位：毫秒
     */
    private Long expire = 30000L;

    /**
     * 获取锁超时时间 单位：毫秒
     */
    private Long acquireTimeout = 3000L;

    /**
     * 获取锁失败时重试时间间隔 单位：毫秒
     */
    private Long retryInterval = 100L;

    /**
     * 默认执行器，不设置默认取容器第一个(默认注入顺序，redisson > redisTemplate > zookeeper)
     */
    private Class<? extends DistributedLockExecutor> primaryExecutor;

    /**
     * 锁key前缀
     */
    private String keyPrefix = "distributedLock";

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Long getAcquireTimeout() {
        return acquireTimeout;
    }

    public void setAcquireTimeout(Long acquireTimeout) {
        this.acquireTimeout = acquireTimeout;
    }

    public Long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Class<? extends DistributedLockExecutor> getPrimaryExecutor() {
        return primaryExecutor;
    }

    public void setPrimaryExecutor(Class<? extends DistributedLockExecutor> primaryExecutor) {
        this.primaryExecutor = primaryExecutor;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}