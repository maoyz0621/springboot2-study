/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.mybaits.plugin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @author maoyz0621 on 2023/4/4
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.myz.springboot2.mybatis.encrypt.domain")
public class MybatisPluginApplication {
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            SpringApplication.run(MybatisPluginApplication.class, args);
            countDownLatch.await();
        } catch (Exception e) {
            log.error("MybatisPluginApplication start error,", e);
        }
        long end = System.currentTimeMillis();
        log.info("MybatisPluginApplication start ok");
        log.info("MybatisPluginApplication start ok {}", end - start);
    }
}