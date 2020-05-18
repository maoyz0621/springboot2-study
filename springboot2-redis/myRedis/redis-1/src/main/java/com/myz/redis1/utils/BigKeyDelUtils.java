/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-15 11:47  Inc. All rights reserved.
 */
package com.myz.redis1.utils;

import com.myz.redis1.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;

/**
 * @author maoyz
 */
public class BigKeyDelUtils {

    Jedis jedis = RedisUtils.getJedis();

    private static final String START_CURSOR = "0";

    public void delKeyHash(String key) {
        String cursor = START_CURSOR;
        ScanParams scanParams = new ScanParams().count(100);
        do {
            ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(key, cursor, scanParams);
            List<Map.Entry<String, String>> result = scanResult.getResult();
            if (result != null && !result.isEmpty()) {
                jedis.hdel(key, result.stream().map(Map.Entry::getKey).toArray(String[]::new));
            }
            cursor = scanResult.getCursor();
        } while (!START_CURSOR.equals(cursor));
        jedis.del(key);
    }

    /**
     * zset 删除bigKey zscan
     *
     * @param key
     */
    public void delKeyZSet(String key) {
        String cursor = START_CURSOR;
        ScanParams scanParams = new ScanParams().count(100);
        do {
            ScanResult<Tuple> scanResult = jedis.zscan(key, cursor, scanParams);
            // 获取扫描结果
            List<Tuple> result = scanResult.getResult();
            if (result != null && !result.isEmpty()) {
                // zst remove 移除集合中一个或多个成员
                jedis.zrem(key, result.stream().map(Tuple::getElement).toArray(String[]::new));
            }
            // 获取游标
            cursor = scanResult.getCursor();
        } while (!START_CURSOR.equals(cursor));
        jedis.del(key);
    }

    /**
     * set 删除bigKey sscan
     *
     * @param key
     */
    public void delKeySet(String key) {
        String cursor = START_CURSOR;
        do {
            ScanResult<String> scanResult = jedis.sscan(key, cursor, new ScanParams().count(100));
            // 获取扫描结果
            List<String> result = scanResult.getResult();
            if (result != null && !result.isEmpty()) {
                // set remove 移除集合中一个或多个成员
                jedis.srem(key, result.toArray(new String[result.size()]));
            }
            // 获取游标
            cursor = scanResult.getCursor();
        } while (!START_CURSOR.equals(cursor));

        //最终删除key
        jedis.del(key);
    }

    /**
     * list 删除bigKey 没有提供lscan命令, 使用ltrim渐进式删除list
     *
     * @param key
     */
    public void delKeyList(String key) {
        Long len = jedis.llen(key);
        System.out.println(len);
        int counter = 0;
        int left = 100;
        while (counter < len) {
            // 左侧截取left个 就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
            jedis.ltrim(key, left, len);
            counter += left;
        }
        //最终删除key
        jedis.del(key);
    }
}