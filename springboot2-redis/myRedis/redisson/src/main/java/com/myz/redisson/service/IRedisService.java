/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.springframework.data.redis.core.*;

/**
 * 使用RedisTemplate 作为服务
 *
 * @author maoyz0621 on 19-7-23
 * @version: v1.0
 */
public interface IRedisService extends IRedisBaseService {

    HashOperations<String, String, Object> hashOperations();

    /**
     * 对redis字符串类型数据操作
     */
    ValueOperations<String, Object> valueOperations();

    /**
     * 对链表类型的数据操作
     */
    ListOperations<String, Object> listOperations();

    /**
     * 对无序集合类型的数据操作
     */
    SetOperations<String, Object> setOperations();

    /**
     * 对有序集合类型的数据操作
     */
    ZSetOperations<String, Object> zSetOperations();
}
