/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.redis.limit.service.lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * redis锁
 * <p>
 * 1 ThreadLocal threadId：通过threadId保存每个线程锁的UUID值，用于区分当前锁是否为自己所有，并且锁的value也存储此值
 * 2 lock主要逻辑：通过BoundValueOperations的setIfAbsent设置lockKey值(setIfAbsent其实就是封装了SETNX的命令），如果返回true，则表示已经获取锁;如果返回false，则进入等待
 * 3 unlock主要逻辑：通过redisTemplate.delete释放锁。在释放锁前，需要判断当前锁被当前线程所有，如果是，才执行释放锁，否则不执行
 * 4 避免死锁：如果线程A拿到锁后，在执行释放锁前，突然死掉了，则其它线程都无法再次获取锁，从而出现死锁。为了避免死锁，我们获取锁后，需要为锁设置一个有效期，即使锁的拥有者死掉了，此锁也可以被自动释放
 * 5 锁可重入：线程A拿到锁后，如果他再次执行lock，也可以再次拿到锁，而不是出现在等待锁的队列中; 如果当前线程已经获取锁，则再次请求锁则一定可以获取锁，否则会出现自己等待自己释放锁，从而出现死锁
 *
 * @author maoyz0621 on 19-1-9
 * @version: v1.0
 */
@Component
public class RedisLock implements IRedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    /**
     * 单位s，一个线程持有锁的最大时间，默认
     */
    private static final int LOCK_MAX_EXIST_TIME = 5;

    /**
     * 锁的key的前缀,默认
     */
    private static final String LOCK_PREX = "lock_redis_";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 锁key的前缀
     */
    private String lockPrex;

    /**
     * 单位s，一个线程持有锁的最大时间
     */
    private int lockMaxExistTime;

    /**
     * 锁名称
     */
    private String lockName;

    private ThreadLocal<String> threadId = new ThreadLocal<>();

    public RedisLock() {
    }

    /**
     * 当没有传lockPrex和lockMaxExistTime时，使用默认值
     */
    public RedisLock(StringRedisTemplate redisTemplate) {
        this(redisTemplate, LOCK_PREX, LOCK_MAX_EXIST_TIME);
    }

    public RedisLock(StringRedisTemplate redisTemplate, String lockPrex, int lockMaxExistTime) {
        this.redisTemplate = redisTemplate;
        this.lockPrex = lockPrex;
        this.lockMaxExistTime = lockMaxExistTime;
    }

    public RedisLock(StringRedisTemplate redisTemplate, String lockPrex, int lockMaxExistTime, String lockName) {
        this.redisTemplate = redisTemplate;
        this.lockPrex = lockPrex;
        this.lockMaxExistTime = lockMaxExistTime;
        this.lockName = lockName;
    }

    @Override
    public void lock() {
        try {
            lock(-1, null);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) throws InterruptedException {
        Assert.notNull(lockName, "lockName can not be null");
        String lockKey = getLockKey();

        // 通过key获取value
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(lockKey);
        while (true) {
            // 如果上次拿到锁的是自己，则本次也可以拿到锁：实现可重入
            String val = valueOps.get();
            // 根据传入的值，判断用户是否持有这个锁
            if (null != val && threadId.get().equals(val)) {
                // 设置过期时间
                valueOps.expire(lockMaxExistTime, TimeUnit.SECONDS);
                break;
            }

            // 没有这个key时
            boolean ifAbsent = valueOps.setIfAbsent(lockKey);
            if (ifAbsent) {
                // 生成
                String keyUniqueId = UUID.randomUUID().toString();
                // 存放本地线程中
                threadId.set(keyUniqueId);
                // 先设置value，再设置过期日期，否则过期日期无效
                valueOps.set(keyUniqueId, lockMaxExistTime, TimeUnit.SECONDS);
                // 为了避免一个用户拿到锁后，进行过程中没有正常释放锁，这里设置一个默认过期实际，这段非常重要，如果没有，则会造成死锁
                // valueOps.expire(lockMaxExistTime, TimeUnit.SECONDS);
                break;
            } else {
                try {
                    // 短暂休眠，避免出现活锁
                    Thread.sleep(10, (int) (Math.random() * 500));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private String getLockKey() {
        StringBuffer sb = new StringBuffer();
        sb.append(lockPrex).append(lockName);
        return sb.toString();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return tryLock(time, -1, unit);
    }


    /**
     * @param waitTime  等待时间
     * @param leaseTime 最小时间
     * @param unit      单位
     * @return 获取锁是否成功
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        // 等待时间
        long time = unit.toMillis(waitTime);
        // 当前时间
        long current = System.currentTimeMillis();
        long threadId = Thread.currentThread().getId();
        if (leaseTime != -1) {

        }

        // 如果锁获取成功了，那现在锁自动释放时间就是最初的锁释放时间减去之前获取锁所消耗的时间。
        time -= System.currentTimeMillis() - current;
        if (time < 0) {
            return false;
        }

        // 如果锁获取失败了，不管是因为获取成功的锁不超过一半（N/2+1)还是因为总消耗时间超过了锁释放时间，客户端都会到每个master节点上释放锁，即便是那些他认为没有获取成功的锁。

        current = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> latch.countDown()).start();
        boolean await = latch.await(leaseTime, unit);
        if (await) {
            return true;
        }

        return true;
    }

    /**
     * 释放锁，同时要考虑当前锁是否为自己所有，以下情况会导致当前线程失去锁：线程执行的时间超过超时的时间，导致此锁被其它线程拿走; 此时用户不可以执行删除
     * <p>
     * 以上方法的缺陷：
     * a. 在本线程获取值，判断锁本线程所有，但是在执行删除前，锁超时被释放同时被另一个线程获取，则本操作释放锁
     * <p>
     * 最终解决方案
     * a. 使用lua脚本，保证检测和删除在同一事物中
     */
    @Override
    public void unlock() {
        final String lockKey = getLockKey();
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(lockKey);
        String lockValue = valueOps.get();
        if (!StringUtils.isEmpty(lockValue) && StringUtils.equals(lockValue, threadId.get())) {
            redisTemplate.delete(lockKey);
            threadId.remove();
        } else {
            logger.warn("key=[{}]已经变释放了，本次不执行释放. 线程Id[{}] ", lockName, lockValue);
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public String getName() {
        return this.lockName;
    }

    @Override
    public boolean isLocked() {
        return false;
    }
}
