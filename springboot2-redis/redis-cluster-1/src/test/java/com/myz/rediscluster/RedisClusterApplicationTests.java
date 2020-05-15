package com.myz.rediscluster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterApplicationTests {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void redisTemplate() {
        redisTemplate.opsForValue().set("author", "redis-cluster-1");
        for (int i = 0; i < 10; i++) {
            // 自增长
            redisTemplate.opsForValue().increment("incre_key", 1);
        }
        BoundValueOperations<String, Serializable> increKey = redisTemplate.boundValueOps("incre_key");
        Serializable serializable = increKey.get();
        System.out.println(serializable);
    }

    @Test
    public void list() {
        redisTemplate.delete("list_key");
        // 获取list操作类
        ListOperations<String, Serializable> listOperations = redisTemplate.opsForList();
        for (int i = 0; i < 10; i++) {
            // list类型
            listOperations.leftPush("list_key", i);
        }
        // list的长度
        Long size = listOperations.size("list_key");

        for (int i = 0; i < size; i++) {
            Serializable list_key = listOperations.rightPop("list_key");
            System.out.println(list_key);
        }
    }

}
