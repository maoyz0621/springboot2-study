/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.config;

import com.myz.distributed.lock.core.DefaultLockKeyGenerator;
import com.myz.distributed.lock.core.DistributeLockFactory;
import com.myz.distributed.lock.core.DistributedLockServer;
import com.myz.distributed.lock.core.LockKeyGenerator;
import com.myz.distributed.lock.core.executor.DistributedLockExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@EnableConfigurationProperties(DistributedLockProperties.class)
public class DistributedLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DistributedLockServer distributedLockServer(DistributeLockFactory distributeLockFactory, DistributedLockProperties distributedLockProperties) {
        return new DistributedLockServer(distributeLockFactory, distributedLockProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DistributeLockFactory distributeLockFactory(List<DistributedLockExecutor> distributedLockExecutors, DistributedLockProperties distributedLockProperties) {
        return new DistributeLockFactory(distributedLockExecutors, distributedLockProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockKeyGenerator lockKeyGenerator() {
        return new DefaultLockKeyGenerator();
    }
}