package com.myz.log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Log4j2Application {


    private static final Logger log = LoggerFactory.getLogger("run");

    public static void main(String[] args) {
        SpringApplication.run(Log4j2Application.class, args);
        log.info("***************** start **********************");
    }

}

