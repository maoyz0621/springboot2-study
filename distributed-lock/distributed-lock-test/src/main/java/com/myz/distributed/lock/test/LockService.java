/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.test;

import com.myz.distribute.lock.spring.annotation.DistributedLock;
import org.springframework.stereotype.Service;

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

    @DistributedLock(keys = {"#addr.id", "#addr.type"})
    public void test1(Addr addr) {
        System.out.println("2222222222");
    }
}