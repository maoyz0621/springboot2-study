/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.jackson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2020/10/30
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
public class Springboot2JacksonApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Springboot2JacksonApplication.class, args);
        } catch (Exception e) {
            log.error("************ Springboot2JacksonApplication start error *************", e);
            throw e;
        }
        log.info("************ Springboot2JacksonApplication start success *************");
    }
}