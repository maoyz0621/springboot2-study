/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.redis.cache.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

/**
 * springboot2以后集成lettuceConnectionFactory连接工厂
 * 集成redis集群(Lettuce)连接
 *
 * @author maoyz on 18-11-29
 * @version: v1.0
 */
@Configuration
@EnableCaching
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(RedisProperties.class)
@Slf4j
public class RedisConfiguration extends CachingConfigurerSupport {

    @Autowired(required = false)
    private AbstractRedisCacheConfiguration abstractRedisCacheConfiguration;

    /**
     * redis缓存的有效时间单位是秒 0代表永久有效
     */
    @Value("${redis.default.expiration:604800L}")
    private long redisDefaultExpiration;

    @Bean("redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        // 全局开启AutoType，不建议使用
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 使用fastjson序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        template.setKeySerializer(new StringRedisSerializer());
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        // 若选择和Redission结合使用,请务必设置
        // template.setHashKeySerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建StringRedisTemplate
     */
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 自定义lettuceConnectionFactory
     */
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory(final RedisProperties redisProperties) {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration(redisProperties.getSentinel().getMaster(),
                new HashSet<>(redisProperties.getSentinel().getNodes()));
        // Caused by: io.lettuce.core.RedisCommandExecutionException: NOAUTH Authentication required. 设置Sentinel密码
        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return new LettuceConnectionFactory(sentinelConfig);
    }

    /**
     * 设置 redis 数据默认过期时间
     * 设置@cacheable 序列化方式
     *
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                .entryTtl(Duration.ofSeconds(redisDefaultExpiration));
        return redisCacheConfiguration;
    }

    /**
     * cacheManager 根据是否有自定义有效时间来动态修改
     *
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        if (abstractRedisCacheConfiguration != null) {
            return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory),
                    redisCacheConfiguration(),
                    abstractRedisCacheConfiguration.getRedisCacheConfigurationMap());
        } else {
            return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory),
                    redisCacheConfiguration());
        }
    }

    /**
     * 显示声明缓存key生成器
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        // return new SimpleKeyGenerator();
        return (target, method, params) -> {
            log.info("*************** 自定义缓存，使用第一参数作为缓存key. params = [{}] ********************", Arrays.toString(params));
            StringBuilder sb = new StringBuilder();
            sb.append("CacheKey:");
            sb.append(target.getClass().getSimpleName());
            sb.append('.').append(method.getName()).append(":");

            // 这里需要注意，参数太多的话考虑使用其他拼接方式
            for (Object obj : params) {
                if (obj != null) {
                    sb.append(obj.toString());
                    sb.append(".");
                }
            }
            return sb.toString();
        };
    }

    /**
     * redis数据操作异常处理 这里的处理：在日志中打印出错误信息，但是放行
     * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
     *
     * @return
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                redisErrorException(exception, key);
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                redisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                redisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                redisErrorException(exception, null);
            }
        };
    }

    /**
     * 异常处理
     *
     * @param exception exception
     */
    protected void redisErrorException(Exception exception, Object key) {
        log.error("redis异常：key = [{}]", key, exception);
    }
}
