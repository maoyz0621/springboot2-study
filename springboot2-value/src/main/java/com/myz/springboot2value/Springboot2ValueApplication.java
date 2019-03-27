package com.myz.springboot2value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 通过@EnableScheduling启动定时任务
 */
@SpringBootApplication
@EnableScheduling
@PropertySource(value = {"classpath:web.properties"})
public class Springboot2ValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ValueApplication.class, args);
    }

}

