package com.myz.maoyz.spring.boot.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本Configuration在MaoyzStarterAutoConfiguration之前加载
 *
 * @author maoyz
 */
@Configuration
public class BeforeStarterAutoConfiguration {

    public BeforeStarterAutoConfiguration() {
        System.out.println("****************** BeforeStarterAutoConfiguration ***************************");
    }

    @Bean
    public MaoyzServiceBefore maoyzBeforeService() {
        return new MaoyzServiceBefore();
    }

}
