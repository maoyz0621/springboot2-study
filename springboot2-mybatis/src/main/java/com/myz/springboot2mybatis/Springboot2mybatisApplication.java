package com.myz.springboot2mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author maoyz0621
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Springboot2mybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2mybatisApplication.class, args);
    }

}

