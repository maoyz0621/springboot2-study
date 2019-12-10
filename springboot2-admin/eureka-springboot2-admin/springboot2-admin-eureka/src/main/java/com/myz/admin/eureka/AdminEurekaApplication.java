package com.myz.admin.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author maoyz
 */
@SpringBootApplication
@EnableEurekaServer
public class AdminEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminEurekaApplication.class, args);
    }
}
