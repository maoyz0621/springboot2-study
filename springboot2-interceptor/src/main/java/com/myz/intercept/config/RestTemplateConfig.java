/**
 * Copyright 2024 Inc.
 **/
package com.myz.intercept.config;

import com.myz.intercept.interceptor.RestClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author maoyz0621 on 2024/4/3
 * @version v1.0
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RestClientInterceptor());
        return restTemplate;
    }
}