package com.myz.springboot2.mybatis.encrypt; /**
 * Copyright 2020 Inc.
 **/

import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptClassScan;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @author maoyz0621 on 2020/9/23
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.myz.springboot2.mybatis.encrypt.domain")
@CryptClassScan(value = {"com.myz.springboot2.mybatis.encrypt.domain"})
public class EncryptApplication {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            SpringApplication.run(EncryptApplication.class, args);
            countDownLatch.await();
        } catch (Exception e) {
            log.error("PageApplication start error,", e);
        }
        long end = System.currentTimeMillis();
        log.info("EncryptApplication start ok");
        log.info("EncryptApplication start ok {}", end - start);
    }

}