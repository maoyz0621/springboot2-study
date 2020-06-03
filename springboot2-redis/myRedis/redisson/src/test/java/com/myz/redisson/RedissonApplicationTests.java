package com.myz.redisson;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonApplicationTests {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() {
        redisTemplate.delete("myMap");
        redisTemplate.delete("myLong");
        redisTemplate.delete("hash");
        //
        // RAtomicLong myLong = redissonClient.getAtomicLong("myLong");
        // myLong.compareAndSet(1L, 2L);
        // myLong.getAndAdd(0);
        // System.out.println(myLong.get());
        // System.out.println(redisTemplate.opsForValue().get("myLong"));

        // String类型
        RBucket<Object> bucket = redissonClient.getBucket("bucket");
        bucket.getAndSet("aa");
        System.out.println(bucket.get());

        // Map类型
        RMap<Object, Object> myMap = redissonClient.getMap("MyMap");
        myMap.putIfAbsent("aa", "111");
        System.out.println(redisTemplate.opsForHash().entries("myMap"));

        // List类型
        RList<Object> list = redissonClient.getList("list");
        List<Object> objects = list.get();

        // Set类型
        RSet<Object> set = redissonClient.getSet("set");

        // RLock锁
        RLock redLock = redissonClient.getRedLock();

        HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
        String key = "hash";
        Map<String, String> map = new HashMap<>();
        map.put("filed1", "value1");
        map.put("filed2", "value2");

        operations.putAll(key, map);
        Map<Object, Object> aa = operations.entries(key);
        // {filed1=value1, filed2=value2}
        System.out.println(aa);

        System.out.println(operations.hasKey(key, "field1"));

        Object field1 = operations.get(key, "field1");
        System.out.println(field1);
    }


}
