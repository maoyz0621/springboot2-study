/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.config;

import com.myz.distribute.lock.spring.aop.DistributedLockAnnotationAdvisor;
import com.myz.distribute.lock.spring.aop.DistributedLockInterceptor;
import com.myz.distributed.lock.core.DistributedLockServer;
import com.myz.distributed.lock.core.LockKeyGenerator;
import com.myz.distributed.lock.core.config.DistributedLockProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * @author maoyz0621 on 2022/5/22
 * @version v1.0
 */
public class DistributedLockInterceptorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DistributedLockInterceptor distributedLockInterceptor(DistributedLockServer distributedLockServer,
                                                                 LockKeyGenerator lockKeyGenerator,
                                                                 DistributedLockProperties distributedLockProperties) {
        return new DistributedLockInterceptor(distributedLockServer, lockKeyGenerator, distributedLockProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DistributedLockAnnotationAdvisor distributedLockAnnotationAdvisor(DistributedLockInterceptor distributedLockInterceptor) {
        return new DistributedLockAnnotationAdvisor(distributedLockInterceptor, Ordered.HIGHEST_PRECEDENCE);
    }
}