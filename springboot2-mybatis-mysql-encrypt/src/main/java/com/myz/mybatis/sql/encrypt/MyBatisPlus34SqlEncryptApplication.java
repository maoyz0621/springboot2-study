/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2023/5/25
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
public class MyBatisPlus34SqlEncryptApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MyBatisPlus34SqlEncryptApplication.class, args);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}