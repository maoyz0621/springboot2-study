package com.myz.springboot2jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(value = "com.myz.springboot2jetty.servlet")
public class Springboot2JettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2JettyApplication.class, args);
    }

}

