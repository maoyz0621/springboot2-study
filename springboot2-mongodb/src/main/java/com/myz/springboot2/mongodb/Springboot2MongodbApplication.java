/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2022/6/16
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
public class Springboot2MongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2MongodbApplication.class, args);
        log.info("****************** Mongodb server Start *******************");
    }
}