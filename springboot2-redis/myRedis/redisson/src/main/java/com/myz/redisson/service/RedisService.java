/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author maoyz0621 on 19-7-23
 * @version: v1.0
 */
@Service
public class RedisService extends AbstractRedisService {

    @Override
    public String get(String key) {
        return (String) valueOperations().get(key);
    }

    @Override
    public <T> Map<String, T> map(String key) {
        return (Map<String, T>) hashOperations().entries(key);
    }

    @Override
    public <T> List<T> list(String key) {
        return (List<T>) listOperations().rightPop(key);
    }

    @Override
    public <T> Set<T> set(String key) {
        // return setOperations().;
        return null;
    }

}
