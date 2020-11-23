/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:37  Inc. All rights reserved.
 */
package com.myz.redis.cache.core;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
public abstract class AbstractRedis {

    protected String key = null;
    protected RedisTemplate redisTemplate;

    public AbstractRedis(String key, RedisTemplate redisTemplate) {
        Assert.notNull(key, "key is null");
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    public boolean exists() {
        return this.redisTemplate.getConnectionFactory().getConnection().exists(this.redisTemplate.getKeySerializer().serialize(this.key));
    }

    public boolean delete() {
        return this.redisTemplate.getConnectionFactory().getConnection().del(new byte[][]{this.redisTemplate.getKeySerializer().serialize(this.key)}) > 0L;
    }

    public Boolean expire(long timeout) {
        return this.redisTemplate.expire(this.key, timeout, TimeUnit.SECONDS);
    }

    public Boolean expireAt(Date date) {
        return this.redisTemplate.expireAt(this.key, date);
    }

    public boolean flush() {
        return (Long) this.redisTemplate.execute((RedisCallback) (redisConnection) -> {
            redisConnection.flushDb();
            return 1L;
        }) > 0L;
    }

    protected void checkResult(@Nullable Object obj) {
        if (obj == null) {
            throw new IllegalStateException("Cannot read collection with Redis connection in pipeline/multi-exec mode");
        }
    }
}