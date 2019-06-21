package com.myz.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 开启异步操作@EnableAsync
 */
@SpringBootApplication
@EnableAsync
public class AsyncApplication {
    private static final Logger log = LoggerFactory.getLogger(AsyncApplication.class);

    public static void main(String[] args) {
        log.info("AsyncApplication Start ");

        SpringApplication.run(AsyncApplication.class, args);
    }


}

