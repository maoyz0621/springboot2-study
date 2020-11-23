/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:39  Inc. All rights reserved.
 */
package com.myz.redis.cache.core;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
public class RedisCache<V> extends AbstractRedis {

    protected final BoundValueOperations<String, V> operations;

    public RedisCache(String key, RedisTemplate redisTemplate) {
        super(key, redisTemplate);
        this.operations = this.redisTemplate.boundValueOps(key);
    }

    public boolean set(V value) {
        return this.set(value, 0L);
    }

    public boolean set(V value, long expired) {
        if (expired > 0L) {
            this.operations.set(value, expired, TimeUnit.SECONDS);
        } else {
            this.operations.set(value);
        }

        return true;
    }

    public V get() {
        return this.operations.get();
    }

    public boolean setnx(V value) {
        return this.operations.setIfAbsent(value);
    }

    public Integer append(String value) {
        return this.operations.append(value);
    }

    public V getAndSet(V newValue) {
        return this.operations.getAndSet(newValue);
    }

    public boolean add(final String key, final String value) {
        Object obj = this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(RedisCache.this.redisTemplate.getStringSerializer().serialize(key), RedisCache.this.redisTemplate.getStringSerializer().serialize(value));
                return true;
            }
        });
        return obj != null && (Boolean) obj;
    }

    public boolean add(final String key, final Long expires, final String value) {
        Object obj = this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(RedisCache.this.redisTemplate.getStringSerializer().serialize(key), expires, RedisCache.this.redisTemplate.getStringSerializer().serialize(value));
                return true;
            }
        });
        return obj != null && (Boolean) obj;
    }
}