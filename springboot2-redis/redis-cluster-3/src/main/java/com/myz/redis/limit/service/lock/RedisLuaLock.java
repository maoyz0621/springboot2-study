/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.redis.limit.service.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * redis锁
 * <p>
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
public class RedisLuaLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLuaLock.class);

    /**
     * 单位s，一个线程持有锁的最大时间，默认
     */
    private static final int LOCK_MAX_EXIST_TIME = 5;
    /**
     * 锁的key的前缀,默认
     */
    private static final String LOCK_PREX = "lock_lua_redis_";

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
     * 锁脚本
     */
    private DefaultRedisScript<Long> lockScript;
    /**
     * 解锁脚本
     */
    private DefaultRedisScript<Long> unlockScript;
    /**
     * 线程变量
     */
    private ThreadLocal<String> threadKeyId = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    /**
     * 当没有传lockPrex和lockMaxExistTime时，使用默认值
     */
    public RedisLuaLock(StringRedisTemplate redisTemplate) {
        this(redisTemplate, LOCK_PREX, LOCK_MAX_EXIST_TIME);
    }

    public RedisLuaLock(StringRedisTemplate redisTemplate, String lockPrex, int lockMaxExistTime) {
        this.redisTemplate = redisTemplate;
        this.lockPrex = lockPrex;
        this.lockMaxExistTime = lockMaxExistTime;
        init();
    }

    /**
     * 初始化脚本
     */
    private void init(){
        lockScript = new DefaultRedisScript<Long>();
        // 设置脚本
        lockScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("classpath:lock.lua")));
        // 设置结果类型
        lockScript.setResultType(Long.class);

        unlockScript = new DefaultRedisScript<Long>();
        unlockScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("classpath:unlock.lua")));
        unlockScript.setResultType(Long.class);
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
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

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
