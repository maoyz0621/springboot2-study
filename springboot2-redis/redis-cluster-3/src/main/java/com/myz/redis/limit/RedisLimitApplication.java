package com.myz.redis.limit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621
 */
@SpringBootApplication(scanBasePackages = {"com.myz.redis"})
public class RedisLimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisLimitApplication.class, args);
    }

}
