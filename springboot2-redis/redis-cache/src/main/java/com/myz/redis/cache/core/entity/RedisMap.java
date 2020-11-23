/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:49  Inc. All rights reserved.
 */
package com.myz.redis.cache.core.entity;

import com.myz.redis.cache.core.AbstractRedis;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author maoyz
 */
public class RedisMap <V> extends AbstractRedis {
    private final BoundHashOperations<String, String, V> operations;

    public RedisMap(String key, RedisTemplate redisTemplate) {
        super(key, redisTemplate);
        this.operations = this.redisTemplate.boundHashOps(this.key);
    }

    public void add(String field, V value) {
        if (!StringUtils.isEmpty(field) && value != null) {
            this.operations.put(field, value);
        }

    }

    public void add(Map<String, V> items) {
        if (items != null && items.size() > 0) {
            this.operations.putAll(items);
        }

    }

    public V get(String field) {
        return this.operations.get(field);
    }

    public Long size() {
        return this.operations.size();
    }

    public Long remove(String... keys) {
        return keys != null && keys.length > 0 ? this.operations.delete(keys) : 0L;
    }

    public void clear() {
        this.operations.getOperations().delete(this.key);
    }

    public List<V> getValues() {
        return this.operations.values();
    }

    public Map<String, V> getEntries() {
        return this.operations.entries();
    }

    public Long increment(String field, long delta) {
        return !StringUtils.isEmpty(field) ? this.operations.increment(field, delta) : 0L;
    }

    public Double increment(String field, double delta) {
        return !StringUtils.isEmpty(field) ? this.operations.increment(field, delta) : 0.0D;
    }

    public boolean containsKey(String key) {
        Boolean result = this.operations.hasKey(key);
        this.checkResult(result);
        return result;
    }
}