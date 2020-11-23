/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 19:42  Inc. All rights reserved.
 */
package com.myz.redis.cache.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ReflectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author maoyz
 */
public class RedisLock implements Lock {
    private static final String OBTAIN_LOCK_SCRIPT = "local lockClientId = redis.call('GET', KEYS[1])\nif lockClientId == ARGV[1] then\n  redis.call('PEXPIRE', KEYS[1], ARGV[2])\n  return true\nelseif not lockClientId then\n  redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])\n  return true\nend\nreturn false";
    private static final long DEFAULT_EXPIRE_AFTER = 60000L;
    private static final long DEFAULT_SLEEP = 100L;
    private static final int DEFAULT_TRY_COUNT = 10;
    private final Logger logger;
    private final String lockKey;
    private final RedisTemplate redisTemplate;
    private final ReentrantLock localLock;
    private volatile long lockedAt;
    private final String clientId;
    private final long expireAfter;
    private final long sleep;
    private final int tryCount;

    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this(redisTemplate, lockKey, 60000L);
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, long expireAfter) {
        this(redisTemplate, lockKey, expireAfter, 100L, 10);
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, long expireAfter, long sleep) {
        this(redisTemplate, lockKey, expireAfter, sleep, 10);
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, long expireAfter, long sleep, int tryCount) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.localLock = new ReentrantLock();
        this.clientId = UUID.randomUUID().toString();
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.expireAfter = expireAfter;
        this.sleep = sleep;
        this.tryCount = tryCount;
    }

    public void lock() {
        this.localLock.lock();

        while (true) {
            int cnt = this.tryCount;

            try {
                while (cnt > 0 && !this.obtainLock()) {
                    Thread.sleep(this.sleep);
                    --cnt;
                }

                return;
            } catch (InterruptedException var3) {
                this.logger.error(var3.getMessage(), var3);
            } catch (Exception var4) {
                this.localLock.unlock();
                this.rethrowAsLockException(var4);
            }
        }
    }

    private void rethrowAsLockException(Exception e) {
        throw new CannotAcquireLockException("Failed to lock mutex at " + this.lockKey, e);
    }

    public void lockInterruptibly() throws InterruptedException {
        this.localLock.lockInterruptibly();

        try {
            while (!this.obtainLock()) {
                Thread.sleep(100L);
            }
        } catch (InterruptedException var2) {
            this.localLock.unlock();
            Thread.currentThread().interrupt();
            throw var2;
        } catch (Exception var3) {
            this.localLock.unlock();
            this.rethrowAsLockException(var3);
        }

    }

    public boolean tryLock() {
        try {
            return this.tryLock(0L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long now = System.currentTimeMillis();
        if (!this.localLock.tryLock(time, unit)) {
            return false;
        } else {
            try {
                long expire = now + TimeUnit.MILLISECONDS.convert(time, unit);

                boolean acquired;
                while (!(acquired = this.obtainLock()) && System.currentTimeMillis() < expire) {
                    Thread.sleep(100L);
                }

                if (!acquired) {
                    this.localLock.unlock();
                }

                return acquired;
            } catch (Exception var9) {
                this.localLock.unlock();
                this.rethrowAsLockException(var9);
                return false;
            }
        }
    }

    private boolean obtainLock() {
        List<String> keys = Collections.singletonList(StringUtils.newStringUtf8(this.redisTemplate.getKeySerializer().serialize(this.lockKey)));
        List<String> args = Arrays.asList(this.clientId, String.valueOf(this.expireAfter));
        Long ret = (Long) RedisUtils.eval(this.redisTemplate, "local lockClientId = redis.call('GET', KEYS[1])\nif lockClientId == ARGV[1] then\n  redis.call('PEXPIRE', KEYS[1], ARGV[2])\n  return true\nelseif not lockClientId then\n  redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])\n  return true\nend\nreturn false", keys, args);
        if (ret != null && ret > 0L) {
            this.lockedAt = System.currentTimeMillis();
        }

        return ret != null && ret > 0L;
    }

    public void unlock() {
        if (!this.localLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("You do not own lock at " + this.lockKey);
        } else if (this.localLock.getHoldCount() > 1) {
            this.localLock.unlock();
        } else {
            try {
                this.redisTemplate.delete(this.lockKey);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Released lock; " + this);
                }
            } catch (Exception var5) {
                ReflectionUtils.rethrowRuntimeException(var5);
            } finally {
                this.localLock.unlock();
            }

        }
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException("Conditions are not supported");
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd@HH:mm:ss.SSS");
        return "RedisLock [lockKey=" + this.lockKey + ",lockedAt=" + dateFormat.format(new Date(this.lockedAt)) + ", clientId=" + this.clientId + "]";
    }

    public int hashCode() {
        int prime = true;
        int result = 1;
        int result = 31 * result + (this.lockKey == null ? 0 : this.lockKey.hashCode());
        result = 31 * result + (int) (this.lockedAt ^ this.lockedAt >>> 32);
        result = 31 * result + this.clientId.hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            RedisLock other = (RedisLock) obj;
            if (!this.lockKey.equals(other.lockKey)) {
                return false;
            } else {
                return this.lockedAt == other.lockedAt;
            }
        }
    }
}