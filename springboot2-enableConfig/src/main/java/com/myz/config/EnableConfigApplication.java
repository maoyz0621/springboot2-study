package com.myz.config; /**
 * Copyright 2021 Inc.
 **/

import com.myz.config.enable.monitor.EnableServerLoadMonitor;
import com.myz.config.enable.validate.EnableValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2021/2/5
 * @version v1.0
 */
@Slf4j
@SpringBootApplication
@EnableValidate(isOpen = true)
@EnableServerLoadMonitor
public class EnableConfigApplication {

    public static void main(String[] args) {
        log.info("EnableConfigApplication Start ");
        SpringApplication.run(EnableConfigApplication.class, args);
    }
}