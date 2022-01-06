/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatisplus34;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2021/12/13
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
public class Mybatisplus34Application {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Mybatisplus34Application.class, args);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}