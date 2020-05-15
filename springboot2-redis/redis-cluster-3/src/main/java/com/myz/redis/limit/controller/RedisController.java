/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.controller;

import com.myz.redis.limit.enums.RedisToken;
import com.myz.redis.limit.service.limit.RateLimitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 19-8-15
 * @version: v1.0
 */
@RestController
public class RedisController {

    @Autowired
    private RateLimitClient rateLimitClient;

    @RequestMapping("/limit")
    public String limit() {
        String key = "abc";
        RedisToken initToken = rateLimitClient.initToken(key);
        if (initToken.isSuccess()) {
            RedisToken token = rateLimitClient.acquireToken(key, 4);
            if (token.isSuccess()) {
                System.out.println("没有触发限流策略");
                return "没有触发限流策略";
            } else {
                System.out.println("触发限流API:调用太忙了,请休息下");
                return "触发限流API:调用太忙了,请休息下";

            }
        }
        return "init redis error";
    }
}
