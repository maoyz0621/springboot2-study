package com.myz.admin.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author maoyz
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AdminEurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminEurekaClientApplication.class, args);
    }

}
