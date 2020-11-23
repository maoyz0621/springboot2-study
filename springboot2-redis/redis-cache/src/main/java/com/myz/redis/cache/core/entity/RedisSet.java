/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:48  Inc. All rights reserved.
 */
package com.myz.redis.cache.core.entity;

import com.myz.redis.cache.core.AbstractRedis;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * @author maoyz
 */
public class RedisSet<V> extends AbstractRedis {

    private final BoundSetOperations<String, V> operations;

    public RedisSet(String key, RedisTemplate redisTemplate) {
        super(key, redisTemplate);
        this.operations = this.redisTemplate.boundSetOps(this.key);
    }

    public boolean add(V... item) {
        if (item != null && item.length > 0) {
            return this.operations.add(item) > 0L;
        } else {
            return false;
        }
    }

    public Set<V> values() {
        return this.operations.members();
    }

    public Long size() {
        return this.operations.size();
    }

    public boolean contain(V item) {
        return this.operations.isMember(item);
    }

    public Long remove(V... items) {
        return items != null && items.length > 0 ? this.operations.remove(items) : 0L;
    }

    public V pop() {
        return this.operations.pop();
    }

    public V random() {
        return this.operations.randomMember();
    }

    public List<V> random(long count) {
        return this.operations.randomMembers(count);
    }

    public void clear() {
        this.delete();
    }
}