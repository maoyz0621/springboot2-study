/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.test;

import com.myz.distribute.lock.spring.annotation.DistributedLock;
import com.myz.distributed.lock.executor.RedisDistributedLockExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/5/13
 * @version v1.0
 */
@Service
public class LockService {

    @DistributedLock(keys = "#name")
    public void test1(Long id, String name) {
        System.out.println("111111111111111111111111111");
    }

    // @DistributedLock(keys = {"#addr.id", "#addr.type"}, lockType = LockType.Reentrant)
    @DistributedLock(keys = {"#addr.id", "#addr.type"}, expire = 2000L, acquireTimeout = 5000L, executor = RedisDistributedLockExecutor.class)
    public void test1(Addr addr) {
        try {
            TimeUnit.MILLISECONDS.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2222222222");
    }
}