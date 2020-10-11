/**
 * Copyright 2020 Inc.
 **/
package com.myz.redis.cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2020/9/8
 * @version v1.0
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Cacheable("#id")
    @Override
    public Map get(Long id) {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "a");
        map.put(2L, "B");
        map.put(3L, "C");
        System.out.println(map);
        return map;
    }
}
