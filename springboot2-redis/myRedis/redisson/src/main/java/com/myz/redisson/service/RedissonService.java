/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author maoyz0621 on 19-7-23
 * @version: v1.0
 */
@Service
public class RedissonService extends AbstractRedissonService {

    @Override
    public String get(String key) {
        return getBucketString(key).get();
    }

    @Override
    public Map<String, String> map(String key) {
        return getMapString(key);
    }

    @Override
    public List list(String key) {
        return getList(key);
    }

    @Override
    public Set set(String key) {
        return getSet(key);
    }

    @Override
    public void add(RLongAdder rLongAdder, Long x) {
        rLongAdder.add(x);
    }

    @Override
    public void increment(RLongAdder rLongAdder) {
        rLongAdder.increment();
    }

    @Override
    public void decrement(RLongAdder rLongAdder) {
        rLongAdder.decrement();
    }

    @Override
    public long sum(RLongAdder rLongAdder) {
        return rLongAdder.sum();
    }

    @Override
    public long add(RAtomicLong atomicLong, Long x) {
        return atomicLong.getAndAdd(x);
    }

    @Override
    public long increment(RAtomicLong atomicLong) {
        return atomicLong.getAndIncrement();
    }

    @Override
    public long decrement(RAtomicLong atomicLong) {
        return atomicLong.getAndDecrement();
    }

    /**
     * 限流器
     *
     * @param rRateLimiter
     * @param rate
     * @param rateInterval
     */
    @Override
    public void limit(RRateLimiter rRateLimiter, long rate, long rateInterval) {
        // one permit per 2 seconds
        rRateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, RateIntervalUnit.SECONDS);
        rRateLimiter.acquire(1L);
    }
}
