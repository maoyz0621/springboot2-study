/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:46  Inc. All rights reserved.
 */
package com.myz.redis.cache.core;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author maoyz
 */
public class RedisCounter<V> extends RedisCache {

    public RedisCounter(String key, RedisTemplate redisTemplate) {
        super(key, redisTemplate);
    }

    public Long increment(long delta) {
        return this.operations.increment(delta);
    }

    public Double increment(double delta) {
        return this.operations.increment(delta);
    }
}