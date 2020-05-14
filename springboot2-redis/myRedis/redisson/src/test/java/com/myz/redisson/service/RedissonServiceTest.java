package com.myz.redisson.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;


/**
 * @author maoyz0621 on 19-7-25
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonServiceTest {

    @Autowired
    private IRedissonService redissonService;

    @Test
    public void get() {
        RBucket<Object> rBucket = redissonService.getBucket("aa");
        // async set value
        boolean b = rBucket.compareAndSet("1", "2");
        // 此时bucketAndSet为旧值
        Object bucketAndSet = rBucket.getAndSet(1111);
        System.out.println(bucketAndSet);
        boolean trySet = rBucket.trySet("aaaaaaa");
        System.out.println(trySet);
        Assert.assertEquals(false, b);

        // sync set value
        rBucket.set("1");
        // 失效时间
        rBucket.expire(30, TimeUnit.MINUTES);
        Object o = rBucket.get();
        Assert.assertEquals("1", o);

        String aa = redissonService.get("aa");
        Assert.assertEquals("1", aa);
    }

    @Test
    public void map() {
        RMap<String, Object> rMap = redissonService.getMap("map");
        // put value
        rMap.putIfAbsent("a", "1");
        rMap.expire(30, TimeUnit.MINUTES);
        Object o = redissonService.map("map").get("a");
        Assert.assertEquals("1", o);

        Object o1 = redissonService.map("map").get("b");
        Assert.assertEquals("2", o1);
    }

    @Test
    public void list() {
    }

    @Test
    public void set() {
    }

    @Test
    public void add() {
        RLongAdder addParam = redissonService.getLongAdder("addParam");
        redissonService.add(addParam, 10L);
        System.out.println(redissonService.sum(addParam));
    }

    @Test
    public void decrement() {
    }

    @Test
    public void sum() {
        RLongAdder addParam = redissonService.getLongAdder("addParam");
        redissonService.add(addParam, 10L);
        redissonService.increment(addParam);
        redissonService.add(addParam, 10L);
        System.out.println(redissonService.sum(addParam));

    }

    @Test
    public void add1() {
        RAtomicLong atom = redissonService.getAtomicLong("atom");
        long add = redissonService.add(atom, 5L);
        System.out.println(add);
        long add1 = redissonService.add(atom, 6L);
        System.out.println(add1);
        System.out.println(atom.getAndAdd(0L));
        atom.expire(30, TimeUnit.MINUTES);
    }

    @Test
    public void increment() {
        RAtomicLong atom = redissonService.getAtomicLong("atom1");
        long add = redissonService.increment(atom);
        System.out.println(add);
        long add1 = redissonService.increment(atom);
        System.out.println(add1);
        System.out.println(atom.getAndAdd(0L));
        atom.expire(30, TimeUnit.MINUTES);
    }

    @Test
    public void testLock() {
        // 创建ThreadFactory
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread_pool_%d").build();
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(
                100,
                150,
                Integer.MAX_VALUE,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 20; i++) {
            int temp = i;
            System.out.println(i);
            fixedThreadPool.execute(() -> {
                RLock lock = redissonService.getRLock("lock");
                try {
                    lock.lock(10, TimeUnit.SECONDS);
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + " 获得锁对象 => " + temp);

                    // Wait for 100 seconds and automatically unlock it after 10 seconds
                    // boolean lockIf = lock.tryLock(20, 10, TimeUnit.SECONDS);
                    // if (lockIf) {
                    //     Thread.sleep(3000L);
                    //     System.out.println(Thread.currentThread().getName() + " 获得锁对象 => " + temp);
                    // }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }

        while (true){

        }

    }

}