/**
 * Copyright 2020 Inc.
 **/
package com.myz.redis.cache.service;

import java.util.Map;

/**
 * @author maoyz0621 on 2020/9/8
 * @version v1.0
 */
public interface CacheService {

    Map get(Long id);
}