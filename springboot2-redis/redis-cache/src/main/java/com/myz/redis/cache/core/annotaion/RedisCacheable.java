/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 21:37  Inc. All rights reserved.
 */
package com.myz.redis.cache.core.annotaion;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
public class RedisCacheable implements Cache {
    private String name;
    private RedisTemplate redisTemplate;
    private long expires = 86400L;

    public RedisCacheable() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        Assert.notNull(key, "key is null");
        String keyRef = key.toString();
        BoundValueOperations<String, Object> operations = this.redisTemplate.boundValueOps(keyRef);
        Object value = operations.get();
        return value != null ? new SimpleValueWrapper(value) : null;
    }

    @Override
    public <T> T get(Object o, @Nullable Class<T> aClass) {
        return null;
    }
    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }
    @Override
    public void put(Object key, Object value) {
        Assert.notNull(key, "key is null");
        Assert.notNull(value, "value is null");
        String keyRef = key.toString();
        BoundValueOperations<String, Object> operations = this.redisTemplate.boundValueOps(keyRef);
        operations.set(value, this.expires, TimeUnit.SECONDS);
    }
    @Override
    public ValueWrapper putIfAbsent(Object o, @Nullable Object o1) {
        return null;
    }
    @Override
    public void evict(Object key) {
        Assert.notNull(key, "key is null");
        String keyRef = key.toString();
        this.redisTemplate.getConnectionFactory().getConnection().del(new byte[][]{this.redisTemplate.getKeySerializer().serialize(keyRef)});
    }
    @Override
    public void clear() {
        this.redisTemplate.execute((redisConnection) -> {
            redisConnection.flushDb();
            return 1L;
        });
    }
}
