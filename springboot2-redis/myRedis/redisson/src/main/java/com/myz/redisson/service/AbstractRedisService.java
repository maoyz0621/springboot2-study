/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

/**
 * @author maoyz0621 on 19-7-23
 * @version: v1.0
 */
public abstract class AbstractRedisService implements IRedisService {

    @Autowired
    protected RedisTemplate redisTemplate;

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    /**
     * 对hash类型的数据操作
     */
    @Override
    public HashOperations<String, String, Object> hashOperations() {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     */
    @Override
    public ValueOperations<String, Object> valueOperations() {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     */
    @Override
    public ListOperations<String, Object> listOperations() {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     */
    @Override
    public SetOperations<String, Object> setOperations() {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     */
    @Override
    public ZSetOperations<String, Object> zSetOperations() {
        return redisTemplate.opsForZSet();
    }
}
