/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.starter;

import com.myz.distributed.lock.executor.RedisDistributedLockExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@ConditionalOnClass(RedisOperations.class)
@Configuration
class RedisDistributedLockAutoConfiguration {

    @Bean
    @Order(150)
    public RedisDistributedLockExecutor redisDistributedLockExecutor(StringRedisTemplate stringRedisTemplate) {
        return new RedisDistributedLockExecutor(stringRedisTemplate);
    }
}