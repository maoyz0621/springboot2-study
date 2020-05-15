/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.redisson.RedissonMultiLock;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 使用RedissonClient 作为服务
 * 每种类型存放常用的String类型, 还有<V>, 还有一种Object类型
 *
 * @author maoyz0621 on 19-7-14
 * @version: v1.0
 */
public interface IRedissonService extends IRedisBaseService {

    RBucket<String> getBucketString(String name);

    RBucket<Object> getBucketObject(String name);

    <V> RBucket<V> getBucket(String name);

    <K, V> RMap<K, V> getMap(String name);

    RMap<String, String> getMapString(String name);

    RMap<String, Object> getMapObject(String name);

    <V> RList<V> getList(String name);

    RList<String> getListString(String name);

    RList<Object> getListObject(String name);

    <V> RSet<V> getSet(String name);

    RSet<String> getSetString(String name);

    RSet<Object> getSetObject(String name);

    RLongAdder getLongAdder(String name);

    RAtomicLong getAtomicLong(String name);

    RRateLimiter getRateLimiter(String name);

    default void limit(RRateLimiter rRateLimiter, long rate, long rateInterval){

    }

    RLock getRLock(String name);

    RedissonMultiLock getRedissonMultiLock(String name);

}
