package com.myz.springboot2servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 开启Servlet扫描
 */
@SpringBootApplication
@ServletComponentScan(value = "com.myz.springboot2servlet")
public class Springboot2ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ServletApplication.class, args);
    }

}

