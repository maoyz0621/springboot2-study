/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author maoyz0621 on 19-4-14
 * @version: v1.0
 */
@Configuration
@EnableCaching
// @AutoConfigureAfter(com.myz.redis.cache.config.RedisConfiguration.class)
public class RedisConfiguration extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    /**
     * lua 工作脚本
     *
     * @return DefaultRedisScript
     */
    @Bean("rateLimitLua")
    public DefaultRedisScript redisScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        // 文本形式
        // redisScript.setScriptText("local rate = redis.call('hget', KEYS[1], 'rate');"
        //         + "local interval = redis.call('hget', KEYS[1], 'interval');"
        //         + "local type = redis.call('hget', KEYS[1], 'type');"
        //         + "assert(rate ~= false and interval ~= false and type ~= false, 'RateLimiter is not initialized')"
        //
        //         + "local valueName = KEYS[2];"
        //         + "if type == '1' then "
        //         + "valueName = KEYS[3];"
        //         + "end;"
        //
        //         + "local currentValue = redis.call('get', valueName); "
        //         + "if currentValue ~= false then "
        //         + "if tonumber(currentValue) < tonumber(ARGV[1]) then "
        //         + "return redis.call('pttl', valueName); "
        //         + "else "
        //         + "redis.call('decrby', valueName, ARGV[1]); "
        //         + "return nil; "
        //         + "end; "
        //         + "else "
        //         + "assert(tonumber(rate) >= tonumber(ARGV[1]), 'Requested permits amount could not exceed defined rate'); "
        //         + "redis.call('set', valueName, rate, 'px', interval); "
        //         + "redis.call('decrby', valueName, ARGV[1]); "
        //         + "return nil; "
        //         + "end;");
        redisScript.setLocation(new ClassPathResource("lua/ratelimit.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * lua初始化脚本
     *
     * @return DefaultRedisScript
     */
    @Bean("rateLimitInitLua")
    public DefaultRedisScript initRedisScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        // 本地lua配置文件
        redisScript.setLocation(new ClassPathResource("lua/ratelimit-init.lua"));
        // redisScript.setScriptText("redis.call('hsetnx', KEYS[1], 'rate', ARGV[1]);"
        //         + "redis.call('hsetnx', KEYS[1], 'interval', ARGV[2]);"
        //         + "return redis.call('hsetnx', KEYS[1], 'type', ARGV[3]);");
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}
