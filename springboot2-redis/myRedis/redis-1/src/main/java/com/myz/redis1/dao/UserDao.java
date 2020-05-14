/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.redis1.dao;

import com.myz.redis1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author maoyz on 2018/4/21
 * @Version: v1.0
 */
@Service
public class UserDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(User user) {
        redisTemplate.opsForValue().set(user.getName(), user.toString());
        // redisTemplate.opsForHash().hasKey(user.getName(), user);
    }

    public void save(String key, Serializable val) {
        redisTemplate.opsForValue().set(key, val);
    }

    public void useCallback() {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // 当前数据库可用的key数量
                Long size = connection.dbSize();
                // Can cast to StringRedisConnection if using a StringRedisTemplate
                ((StringRedisConnection) connection).set("key", "value");
                return size;
            }
        });
    }
}
