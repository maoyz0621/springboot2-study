/**
 * Copyright 2021 Inc.
 **/
package com.myz.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Slf4j
@SpringBootApplication
public class EncryptAndDecryptApplication {

    public static void main(String[] args) {
        log.info("EncryptAndDecryptApplication Start ");
        SpringApplication.run(EncryptAndDecryptApplication.class, args);
    }
}