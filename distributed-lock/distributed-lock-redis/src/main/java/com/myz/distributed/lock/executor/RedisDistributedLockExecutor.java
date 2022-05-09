/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.executor;

import com.myz.distributed.lock.core.executor.AbstractDistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @author maoyz0621 on 2022/5/9
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class RedisDistributedLockExecutor extends AbstractDistributedLockExecutor<String> {

    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<>("return redis.call('set',KEYS[1]," +
            "ARGV[1],'NX','PX',ARGV[2])", String.class);

    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<>("if redis.call('get',KEYS[1]) " +
            "== ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);

    private static final String LOCK_SUCCESS = "OK";

    private final StringRedisTemplate redisTemplate;

    @Override
    public String acquire(String lockKey, String lockVal, long expire, long acquireTimeout) {
        return null;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockVal, String lockInstance) {
        return false;
    }
}