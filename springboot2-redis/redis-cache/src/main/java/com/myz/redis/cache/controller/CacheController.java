/**
 * Copyright 2020 Inc.
 **/
package com.myz.redis.cache.controller;

import com.myz.redis.cache.service.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author maoyz0621 on 2020/9/8
 * @version v1.0
 */
@RestController
public class CacheController {
    @Resource
    CacheService cacheService;

    @GetMapping("/get")
    public String get(Long id) {
        return cacheService.get(id).toString();
    }
}