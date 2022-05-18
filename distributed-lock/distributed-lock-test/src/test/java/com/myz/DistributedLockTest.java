/**
 * Copyright 2022 Inc.
 **/
package com.myz;


import com.myz.distribute.lock.spring.annotation.EnableDistributedLock;
import com.myz.distributed.lock.test.Addr;
import com.myz.distributed.lock.test.LockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author maoyz0621 on 2022/5/13
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributedLockTest.class)
@SpringBootApplication(scanBasePackages = "com.myz.distributed.lock")
@EnableDistributedLock
public class DistributedLockTest {

    @Resource
    LockService lockService;

    @Test
    public void test1() {
        lockService.test1(1L, "myz");
    }

    @Test
    public void test2() {
        lockService.test1(new Addr().setId(2L).setType("a"));
    }
}