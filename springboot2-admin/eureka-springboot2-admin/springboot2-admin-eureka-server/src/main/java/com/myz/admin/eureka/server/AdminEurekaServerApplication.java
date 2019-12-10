package com.myz.admin.eureka.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author maoyz
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class AdminEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminEurekaServerApplication.class, args);
    }

}
