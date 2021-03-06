package com.myz.springboot2task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 通过@EnableScheduling启动定时任务
 */
@SpringBootApplication
@EnableScheduling
public class Springboot2TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2TaskApplication.class, args);
    }

}

