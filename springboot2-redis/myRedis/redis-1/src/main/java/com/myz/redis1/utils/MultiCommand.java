/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-18 11:47  Inc. All rights reserved.
 */
package com.myz.redis1.utils;

import com.myz.redis1.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author maoyz
 */
public class MultiCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("a", "a");
        // 执行命令
        pipeline.sync();


        // pipeline 开启事务
        Response<String> response = pipeline.multi();
        // pipeline 执行
        pipeline.exec();

    }
}