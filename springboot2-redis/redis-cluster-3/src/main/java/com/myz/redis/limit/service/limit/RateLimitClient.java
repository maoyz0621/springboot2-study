/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.service.limit;

import com.google.common.base.Objects;
import com.myz.redis.limit.constants.RedisRateLimitConstant;
import com.myz.redis.limit.enums.RedisToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


/**
 * @author maoyz0621 on 19-4-14
 * @version: v1.0
 */
@Service
public class RateLimitClient {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitClient.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Qualifier("rateLimitLua")
    @Autowired
    private RedisScript<Long> rateLimitLua;

    @Qualifier("rateLimitInitLua")
    @Autowired
    private RedisScript<Long> rateLimitInitLua;

    private boolean inited = false;

    /**
     * 初始化
     *
     * @return
     */
    public RedisToken initToken(String key) {
        RedisToken token = RedisToken.SUCCESS;
        if (!inited) {
            Long currentMillSeconds = redisTemplate.execute((RedisCallback<Long>) connection -> connection.time());
            logger.info("************ initToken currentMillSeconds = {}**************", currentMillSeconds);
            logger.info("{}", currentMillSeconds);

            Long acquire = redisTemplate.execute(
                    rateLimitInitLua,
                    Collections.singletonList(getKey(key)),
                    // 最后时间毫秒
                    // 当前可用的令牌
                    // 令牌桶最大值
                    // 每秒生成几个令牌
                    // 应用
                    Optional.ofNullable(currentMillSeconds).orElse(1L).toString(), "1", "50", "10", "skynet");

            logger.info("********* initToken acquire = [{}] *************", acquire);

            if (!Objects.equal(acquire, 1L) && !Objects.equal(acquire, 0L)) {
                logger.error("********* initToken failed *************");
                token = RedisToken.FAILED;
            }
            setInited(true);
        }
        return token;
    }

    /**
     * 获得key操作
     *
     * @return
     */
    public RedisToken acquireToken(String key) {
        return acquireToken(key, 1);
    }

    /**
     * @param key key值
     * @param permits 许可
     * @return 是否获取到令牌
     */
    public RedisToken acquireToken(String key, Integer permits) {
        RedisToken token = RedisToken.SUCCESS;
        Long currentMillSeconds = redisTemplate.execute((RedisCallback<Long>) connection -> connection.time());
        logger.info("************ acquireToken currentMillSeconds = {}**************", currentMillSeconds);

        Long acquire = redisTemplate.execute(
                rateLimitLua,
                Collections.singletonList(getKey(key)),
                String.valueOf(permits),
                String.valueOf(currentMillSeconds));

        logger.info("*********acquireToken acquire = [{}] *************", acquire);

        // 设置key失效时间
        // redisTemplate.expire(getKey(key), 30, TimeUnit.SECONDS);

        if (!Objects.equal(acquire, 1L)) {
            token = RedisToken.FAILED;
        }
        return token;
    }

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    public String getKey(String key) {
        return RedisRateLimitConstant.RATE_LIMIT_KEY + key;
    }

    public void setInited(boolean inited) {
        this.inited = inited;
    }
}
