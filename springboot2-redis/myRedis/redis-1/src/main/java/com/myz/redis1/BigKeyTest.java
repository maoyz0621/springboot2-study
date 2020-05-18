/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-15 11:25  Inc. All rights reserved.
 */
package com.myz.redis1;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
public class BigKeyTest {

    Jedis jedis;

    @Before
    public void before() {
        jedis = RedisUtils.getJedis();
        jedis.select(2);
    }

    @After
    public void close() {
        RedisUtils.close(jedis);
    }

    @Test
    public void testBigKey() {
        // str();

        // list();

        // set();

        // zset();

        hash();

    }

    private void hash() {
        hashString();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        long start = System.currentTimeMillis();
        delKeyHash("hash");
        long end = System.currentTimeMillis();
        System.out.println("execute: " + (end - start));
    }


    private void zset() {
        zsetString();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        long start = System.currentTimeMillis();
        delKeyZSet("zset");
        // delKeyString("zset");
        long end = System.currentTimeMillis();
        System.out.println("execute: " + (end - start));
    }

    /**
     * 删除set key
     */
    private void set() {
        setString();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        long start = System.currentTimeMillis();
        delKeySet("set");
        // delKeyString("set");
        long end = System.currentTimeMillis();
        System.out.println("execute: " + (end - start));
    }

    /**
     * 删除list key
     */
    private void list() {
        listString();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        long start = System.currentTimeMillis();
        // delKeyList("list");
        delKeyString("list");
        long end = System.currentTimeMillis();
        System.out.println("execute: " + (end - start));
    }

    /**
     * 删除String key
     */
    private void str() {
        keyString();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        long start = System.currentTimeMillis();
        delKeyString("str");
        long end = System.currentTimeMillis();
        System.out.println("execute: " + (end - start));
    }

    private void delKeyString(String key) {
        jedis.del(key);
    }

    private void delKeyHash(String key) {
        String cursor = "0";
        do {
            ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(key, cursor, new ScanParams().count(100));
            List<Map.Entry<String, String>> result = scanResult.getResult();
            if (result != null && !result.isEmpty()) {
                jedis.hdel(key, result.stream().map(Map.Entry::getKey).toArray(String[]::new));
            }
            cursor = scanResult.getCursor();
        } while (!"0".equals(cursor));
        jedis.del(key);
    }


    private void delKeyZSet(String key) {
        String cursor = "0";
        while (true) {
            ScanResult<Tuple> scanResult = jedis.zscan(key, cursor, new ScanParams().count(100));
            // 获取游标
            cursor = scanResult.getCursor();
            // 获取扫描结果
            List<Tuple> result = scanResult.getResult();
            if (result == null || result.size() == 0) {
                continue;
            }

            // 移除集合中一个或多个成员
            jedis.zrem(key, result.stream().map(Tuple::getElement).toArray(String[]::new));

            if ("0".equals(cursor)) {
                break;
            }
        }
        jedis.del(key);
    }

    private void delKeySet(String key) {
        String cursor = "0";
        while (true) {
            ScanResult<String> scanResult = jedis.sscan(key, cursor, new ScanParams().count(100));
            // 获取游标
            cursor = scanResult.getCursor();
            // 获取扫描结果
            List<String> result = scanResult.getResult();
            if (result == null || result.size() == 0) {
                continue;
            }

            // 移除集合中一个或多个成员
            jedis.srem(key, result.toArray(new String[result.size()]));

            if ("0".equals(cursor)) {
                break;
            }
        }

        //最终删除key
        jedis.del(key);
    }

    /**
     * list 没有提供lscan命令, 使用ltrim渐进式删除list
     *
     * @param key
     */
    private void delKeyList(String key) {
        Long llen = jedis.llen(key);
        System.out.println(llen);
        int counter = 0;
        int left = 100;
        while (counter < llen) {
            jedis.ltrim(key, left, llen);
            counter += left;
        }
        //最终删除key
        jedis.del(key);
    }

    private void keyString() {
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < 1024 * 1024; i++) {
            val.append("aaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        for (int i = 0; i < 1024 * 1024; i++) {
            val.append("aaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        jedis.set("str", val.toString());
    }

    private void listString() {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 1024; i++) {
            list.add("aaaaaaaaaaaaaaaaaaaaaaaaaa" + i);
        }
        // for (int i = 0; i < 1024 * 1024; i++) {
        //     list.add("bbbbbbbbbbbbbbbbbbbbbbbbbb" + i);
        // }
        jedis.lpush("list", list.toArray(new String[list.size()]));
    }

    private void setString() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            set.add("aaaaaaaaaaaaaaaaaaaaaaaaaa" + i);
        }
        jedis.sadd("set", set.toArray(new String[set.size()]));
    }

    private void zsetString() {
        Map<String, Double> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put("aaaaaaaaaaaaaaaaaaaaaaaaaa" + i, (double) i);
        }
        jedis.zadd("zset", map);
    }

    private void hashString() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put("a" + i, i + "");
        }
        jedis.hmset("hash", map);
        System.out.println(jedis.hkeys("hash"));
        // 获取所有给定字段的值
        System.out.println(jedis.hmget("hash", "a1"));
        System.out.println(jedis.hlen("hash"));
    }
}