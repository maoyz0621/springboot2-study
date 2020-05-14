package com.myz.redisson.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author maoyz0621 on 19-7-25
 * @version: v1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    IRedisService redisService;

    @Test
    public void get() {
        String aa = redisService.get("aa");
        Assert.assertEquals("1", aa);
    }

    @Test
    public void map() {
        // 存放值必须通过HashOperations 操作
        HashOperations<String, String, Object> hashOperations = redisService.hashOperations();
        hashOperations.put("map", "c", "3");
        redisService.hashOperations().put("map", "a1", "11");

        // entries之后无法在赋值
        hashOperations.entries("map").putIfAbsent("d", "4");
        Map<String, Object> map = redisService.map("map");
        map.putIfAbsent("b", "2");
        Assert.assertEquals("2", map.get("b"));


        Object a = map.get("a");
        Assert.assertEquals("1", a);
    }

    @Test
    public void list() {
    }

    @Test
    public void set() {
    }
}