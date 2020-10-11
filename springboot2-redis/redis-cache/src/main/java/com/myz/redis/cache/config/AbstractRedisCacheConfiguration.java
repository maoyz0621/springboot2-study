/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.cache.config;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maoyz0621 on 19-7-14
 * @version: v1.0
 */
public abstract class AbstractRedisCacheConfiguration {

    /**
     * 子类重写, 获取需要特殊处理的cacheNames
     *
     * @return
     */
    public abstract Map<String, Long> getCacheNamesTtl();

    /**
     * 返回需要特殊处理的RedisCacheConfiguration
     *
     * @return Map<String,RedisCacheConfiguration>
     * String:cacheName
     * RedisCacheConfiguration:getRedisCacheConfigurationWithTtl(long seconds)
     */
    public Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        final Map<String, RedisCacheConfiguration> result = new ConcurrentHashMap<>();
        final Map<String, Long> cacheNamesTtl = getCacheNamesTtl();
        if (CollectionUtils.isEmpty(cacheNamesTtl)) {
            cacheNamesTtl.forEach((k, v) ->
                    result.put(k, fastJsonRedisCacheConfigurationWithTtl(v))
            );
        }
        return result;
    }

    /**
     * 获取通用的RedisCacheConfiguration
     */
    // private RedisCacheConfiguration fastJsonRedisCacheConfigurationWithTtl(long seconds) {
    //     FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
    //     ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    //     RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
    //             .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
    //             .entryTtl(Duration.ofSeconds(seconds));
    //     return configuration;
    // }

    private RedisCacheConfiguration fastJsonRedisCacheConfigurationWithTtl(long seconds) {
        Jackson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                .entryTtl(Duration.ofSeconds(seconds));
        return configuration;
    }
}
