/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.redisson.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class RedisConfiguration extends CachingConfigurerSupport {

    /**
     * redis缓存的有效时间单位是秒 0代表永久有效, 默认保存一周时间
     */
    @Value("${redis.default.expiration:604800}")
    private long redisDefaultExpiration;

    @Bean("redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());

        // 使用fastjson序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        // hash类型
        template.setHashKeySerializer(fastJsonRedisSerializer);
        // template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.setConnectionFactory(lettuceConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 自定义lettuceConnectionFactory -> RedisSentinelConfiguration, 哨兵配置
     * 依赖配置 spring.redis.sentinel
     */
    @Bean
    @ConditionalOnProperty("spring.redis.sentinel")
    public RedisConnectionFactory lettuceConnectionFactory(final RedisProperties redisProperties) {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration(redisProperties.getSentinel().getMaster(),
                new HashSet<>(redisProperties.getSentinel().getNodes()));
        // Caused by: io.lettuce.core.RedisCommandExecutionException: NOAUTH Authentication required. 设置Sentinel密码
        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return new LettuceConnectionFactory(sentinelConfig);
    }

    /**
     * 自定义lettuceConnectionFactory -> RedisClusterConfiguration, 集群配置
     * 依赖配置 spring.redis.cluster
     */
    @Bean
    @ConditionalOnProperty("spring.redis.cluster")
    public RedisConnectionFactory redisClusterConfiguration(final RedisProperties redisProperties) {
        final Map<String, Object> source = new HashMap<>(16);
        source.put("spring.redis.cluster.nodes", redisProperties.getCluster().getNodes());
        source.put("spring.redis.cluster.timeout", redisProperties.getTimeout());
        source.put("spring.redis.cluster.max-redirects", redisProperties.getCluster().getMaxRedirects());
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
        return new LettuceConnectionFactory(redisClusterConfiguration);
    }

    /**
     * redisson缓存
     *
     * @param redissonClient redisson客户端
     * @return
     */
    @Bean
    @Primary
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>(16);
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    /**
     * cacheManager 根据是否有自定义有效时间来动态修改
     *
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory),
                redisCacheConfiguration());

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

}
