package com.myz.springboot2mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621
 */
@SpringBootApplication
@Slf4j
public class Springboot2MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2MailApplication.class, args);
        log.info("****************** 邮件服务 Start *******************");
    }

}

