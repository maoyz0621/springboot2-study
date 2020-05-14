/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RLongAdder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author maoyz0621 on 19-7-23
 * @version: v1.0
 */
public interface IRedisBaseService {

    String get(String key);

    <T> Map<String, T> map(String key);

    <T> List<T> list(String key);

    <T> Set<T> set(String key);

    default void add(RLongAdder rLongAdder, Long x) {
    }

    default void increment(RLongAdder rLongAdder) {
    }

    default void decrement(RLongAdder rLongAdder) {
    }

    default long sum(RLongAdder rLongAdder) {
        return 0L;
    }

    default long add(RAtomicLong atomicLong, Long x) {return 0L;

    }

    default long increment(RAtomicLong atomicLong) {
        return 0L;
    }

    default long decrement(RAtomicLong atomicLong) {
        return 0L;
    }

    default long sum(RAtomicLong atomicLong) {
        return 0L;
    }
}
