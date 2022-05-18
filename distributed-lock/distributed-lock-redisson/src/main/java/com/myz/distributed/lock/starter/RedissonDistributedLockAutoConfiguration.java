/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.starter;

import com.myz.distributed.lock.executor.RedissonDistributedLockExecutor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 某个class位于类路径上，才会实例化一个Bean @ConditionalOnClass
 *
 * @author maoyz0621 on 2022/5/11
 * @version v1.0
 */
@ConditionalOnClass(RedissonClient.class)
@Configuration
class RedissonDistributedLockAutoConfiguration {

    @Bean
    @Order(100)
    public RedissonDistributedLockExecutor redissonDistributedLockExecutor(RedissonClient redissonClient) {
        return new RedissonDistributedLockExecutor(redissonClient);
    }
}