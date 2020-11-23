/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:47  Inc. All rights reserved.
 */
package com.myz.redis.cache.core.entity;

import com.myz.redis.cache.core.AbstractRedis;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author maoyz
 */
public class RedisList<V> extends AbstractRedis {
    private final BoundListOperations<String, V> operations;

    public RedisList(String key, RedisTemplate redisTemplate) {
        super(key, redisTemplate);
        this.operations = this.redisTemplate.boundListOps(this.key);
    }

    public boolean add(V item) {
        if (item != null) {
            return this.operations.rightPush(item) > 0L;
        } else {
            return false;
        }
    }

    public V set(int index, V item) {
        V old = this.get((long)index);
        this.operations.set((long)index, item);
        return old;
    }

    public Long batchAdd(V... items) {
        return items != null && items.length > 0 ? this.operations.rightPushAll(items) : 0L;
    }

    public boolean unshift(V item) {
        if (item != null) {
            return this.operations.leftPush(item) > 0L;
        } else {
            return false;
        }
    }

    public Long batchUnshift(V... items) {
        return items != null && items.length > 0 ? this.operations.leftPushAll(items) : 0L;
    }

    public List<V> range(long start, long end) {
        return this.operations.range(start, end);
    }

    public int size() {
        return this.operations.size().intValue();
    }

    public V rPop() {
        return this.operations.rightPop();
    }

    public V lPop() {
        return this.operations.leftPop();
    }

    public V get(long idx) {
        return this.operations.index(idx);
    }

    public boolean remove(V item) {
        return item != null ? this.remove(item, 0L) : false;
    }

    public boolean remove(V item, long count) {
        if (item == null) {
            return false;
        } else {
            Long result = this.operations.remove(count, item);
            return result != null && result > 0L;
        }
    }

    public RedisList trim(long start, long end) {
        this.operations.trim(start, end);
        return this;
    }

    public void clear() {
        this.trim((long)(this.size() + 1), 0L);
    }
}
