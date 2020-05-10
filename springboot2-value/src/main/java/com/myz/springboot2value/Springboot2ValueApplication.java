package com.myz.springboot2value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 通过@EnableScheduling启动定时任务
 *
 * @author maoyz0621
 */
@SpringBootApplication
@PropertySource(value = {"classpath:web.properties"})
public class Springboot2ValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ValueApplication.class, args);
    }

}

