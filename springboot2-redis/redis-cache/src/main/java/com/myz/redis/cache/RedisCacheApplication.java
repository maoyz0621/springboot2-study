package com.myz.redis.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisCacheApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(RedisCacheApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
