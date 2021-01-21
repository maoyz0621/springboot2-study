package com.myz.springboot2.mybatis.page; /**
 * Copyright 2020 Inc.
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
public class PageApplication {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            SpringApplication.run(PageApplication.class, args);
            countDownLatch.await();
        } catch (Exception e) {
            log.error("PageApplication start error,", e);
        }
        long end = System.currentTimeMillis();
        log.info("PageApplication start ok");
        log.info("PageApplication start ok {}", end - start);
    }

}