/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.rediscluster.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.collections.RedisProperties;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * redis集群配置，使用JedisCluster
 *
 * @author maoyz on 18-12-2
 * @version: v1.0
 */
@Configuration
@Slf4j
public class RedisClusterConfig {

    @Value("${spring.redis.commandTimeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.commandTimeout}")
    private int commandTimeout;
    private static Set<HostAndPort> NODES;

    /**
     * 分割出集群节点
     *
     * @param nodes
     */
    @Value("${spring.redis.cluster.nodes}")
    public void setNodes(String nodes) {
        try {
            Set<HostAndPort> hostAndPortSet = new LinkedHashSet<HostAndPort>();
            String[] hostAndPorts = nodes.split(",");
            for (String hap : hostAndPorts) {
                String ip = hap.split(":")[0];
                int port = Integer.parseInt(hap.split(":")[1]);

                HostAndPort hostAndPort = new HostAndPort(ip, port);

                hostAndPortSet.add(hostAndPort);
            }
            NODES = hostAndPortSet;
        } catch (Exception e) {
            log.error("集群节点配置有误");
            throw e;
        }
    }

    /**
     * 创建JedisCluster
     */
    @Bean
    public JedisCluster getJedisCluster() {
        // redis连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMinIdle(minIdle);
        //创建集群对象
        return new JedisCluster(NODES, commandTimeout, jedisPoolConfig);
    }

}
