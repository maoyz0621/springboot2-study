/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.jackson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.springboot2.jackson.config.format.DateToLongFormatterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author maoyz0621 on 2020/10/30
 * @version v1.0
 */
@Configuration
public class JacksonConfig implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
       ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new DateToLongFormatterFactory());
    }

}