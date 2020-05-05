/*
 * Copyright (C) 2019, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-04-29 17:31  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author maoyz
 */
@SpringBootApplication
@PropertySource(value = {"classpath:jdbc.properties"})
public class ShardingjdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcApplication.class, args);
    }
}
