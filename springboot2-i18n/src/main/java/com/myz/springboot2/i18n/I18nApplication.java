/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2020/9/29
 * @version v1.0
 */
@SpringBootApplication
@Slf4j
public class I18nApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(I18nApplication.class, args);
        } catch (Exception e) {
            log.error("************ I18nApplication start error *************", e);
        }
        log.info("************ I18nApplication start success *************");
    }
}