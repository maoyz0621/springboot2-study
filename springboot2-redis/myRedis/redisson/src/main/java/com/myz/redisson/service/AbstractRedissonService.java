/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 19-7-23
 * @version: v1.0
 */
public abstract class AbstractRedissonService implements IRedissonService {

    @Autowired
    protected RedissonClient redissonClient;

    @Override
    public RBucket<String> getBucketString(String name) {
        return getBucket(name);
    }

    @Override
    public RBucket<Object> getBucketObject(String name) {
        return getBucket(name);
    }

    @Override
    public <V> RBucket<V> getBucket(String name) {
        return redissonClient.getBucket(name);
    }

    @Override
    public <K, V> RMap<K, V> getMap(String name) {
        return redissonClient.getMap(name);
    }

    @Override
    public RMap<String, String> getMapString(String name) {
        return getMap(name);
    }

    @Override
    public RMap<String, Object> getMapObject(String name) {
        return getMap(name);
    }

    @Override
    public <V> RList<V> getList(String name) {
        return redissonClient.getList(name);
    }

    @Override
    public RList<String> getListString(String name) {
        return getList(name);
    }

    @Override
    public RList<Object> getListObject(String name) {
        return getList(name);
    }

    @Override
    public <V> RSet<V> getSet(String name) {
        return redissonClient.getSet(name);
    }

    @Override
    public RSet<String> getSetString(String name) {
        return getSet(name);
    }

    @Override
    public RSet<Object> getSetObject(String name) {
        return getSet(name);
    }

    @Override
    public RLongAdder getLongAdder(String name) {
        return redissonClient.getLongAdder(name);
    }

    @Override
    public RAtomicLong getAtomicLong(String name) {
        return redissonClient.getAtomicLong(name);
    }

    @Override
    public RRateLimiter getRateLimiter(String name) {
        return redissonClient.getRateLimiter(name);
    }

    @Override
    public RLock getRLock(String name) {
        return redissonClient.getLock(name);
    }

    @Override
    public RedissonMultiLock getRedissonMultiLock(String name) {
        return new RedissonRedLock(getRLock(name));
    }
}
