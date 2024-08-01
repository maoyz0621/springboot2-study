package com.myz.springboot2.monitor;

import com.myz.springboot2.monitor.config.StartTimeMetric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Hello world!
 */
@SpringBootApplication
public class StatisticsApplication {

    //@Profile("dev")
    @Bean
    public StartTimeMetric timedBeanPostProcessor() {
        return new StartTimeMetric();
    }

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

}