/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.redis1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池
 *
 * @author maoyz on 2018/4/20
 * @Version: v1.0
 */
public class RedisUtils {

    private final static String HOST_NAME = "127.0.0.1";
    private final static int PORT = 6379;
    private final static int TIMEOUT = 10000;
    private final static String PASSWORD = "root";
    private final static JedisPool jedisPool;

    // 连接池
    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxIdle(300);
        jedisPool = new JedisPool(poolConfig, HOST_NAME, PORT, TIMEOUT);
    }

    private RedisUtils() {
    }

    public synchronized static Jedis getJedis() {
        if (jedisPool != null) {
            return jedisPool.getResource();
        }
        return null;
    }

    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
