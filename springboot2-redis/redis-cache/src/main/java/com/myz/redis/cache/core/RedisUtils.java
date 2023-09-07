/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:42  Inc. All rights reserved.
 */
package com.myz.redis.cache.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz
 */
public class RedisUtils implements ApplicationContextAware {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static ApplicationContext applicationContext;

    public RedisUtils() {
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisUtils.applicationContext = applicationContext;
    }

    public static RedisUtils getBean() {
        return (RedisUtils)applicationContext.getBean(RedisUtils.class);
    }

    public static Object eval(RedisTemplate redisTemplate, String script, List<String> keys, List<String> args) {
        return redisTemplate.execute((connection) -> {
            Object nativeConnection = connection.getNativeConnection();
            if (nativeConnection instanceof JedisCluster) {
                return ((JedisCluster)nativeConnection).eval(script, keys, args);
            } else {
                return nativeConnection instanceof RedisProperties.Jedis ? ((Jedis)nativeConnection).eval(script, keys, args) : null;
            }
        });
    }

    public boolean add(final String key, final String value) {
        Object obj = this.redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(RedisUtils.this.redisTemplate.getStringSerializer().serialize(key), RedisUtils.this.redisTemplate.getStringSerializer().serialize(value));
                return true;
            }
        });
        return obj != null && (Boolean)obj;
    }

    public boolean add(final String key, final Long expires, final String value) {
        Object obj = this.redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(RedisUtils.this.redisTemplate.getStringSerializer().serialize(key), expires, RedisUtils.this.redisTemplate.getStringSerializer().serialize(value));
                return true;
            }
        });
        return obj != null && (Boolean)obj;
    }

    public boolean add(final Map<String, String> map) {
        Assert.notEmpty(map);
        boolean result = (Boolean)this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisUtils.this.redisTemplate.getStringSerializer();
                Iterator var3 = map.entrySet().iterator();

                while(var3.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry)var3.next();
                    byte[] key = serializer.serialize(entry.getKey());
                    byte[] name = serializer.serialize(entry.getValue());
                    connection.setNX(key, name);
                }

                return true;
            }
        }, false, true);
        return result;
    }

    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    public boolean update(final String key, final String value) {
        if (this.get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        } else {
            boolean result = (Boolean)this.redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = RedisUtils.this.redisTemplate.getStringSerializer();
                    connection.set(serializer.serialize(key), serializer.serialize(value));
                    return true;
                }
            });
            return result;
        }
    }

    public Object get(final String keyId) {
        Object result = this.redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisUtils.this.redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = connection.get(key);
                return value == null ? null : serializer.deserialize(value);
            }
        });
        return result;
    }
}