package com.myz.config; /**
 * Copyright 2021 Inc.
 **/

import com.myz.config.enable.monitor.config.EnableServerLoadMonitor;
import com.myz.config.enable.validate.ValidateType;
import com.myz.config.enable.validate.config.EnableValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 2021/2/5
 * @version v1.0
 */
@SpringBootApplication
@EnableValidate(isOpen = true, policy = ValidateType.CORE)
@EnableServerLoadMonitor
public class EnableConfigApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableConfigApplication.class);


    public static void main(String[] args) {
        LOGGER.info("EnableConfigApplication Start ");
        SpringApplication.run(EnableConfigApplication.class, args);

    }
}