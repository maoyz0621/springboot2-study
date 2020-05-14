package com.myz.rediscluster2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCluster2ApplicationTests {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisTemplate() {
        redisTemplate.opsForValue().set("author", "Damein_xym211");
    }

    @Test
    public void stringRedisTemplate() {
        stringRedisTemplate.opsForValue().set("stringRedisTemplate", "stringRedisTemplate");
    }
}
