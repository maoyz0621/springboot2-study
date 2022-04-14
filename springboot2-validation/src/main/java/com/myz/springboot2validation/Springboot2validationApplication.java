package com.myz.springboot2validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621
 */
@SpringBootApplication
@Slf4j
public class Springboot2validationApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Springboot2validationApplication.class, args);
            log.info("================= 服务启动成功 ==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

