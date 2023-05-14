/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.encrypt.config.converter.MappingEncryptAndDecryptHttpMessageConverter;
import com.myz.encrypt.config.strategy.EncryptConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author maoyz0621 on 2023/5/10
 * @version v1.0
 */
@Configuration
public class ConverterConfiguration {

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    @Autowired
    private EncryptConverter encryptConverter;

    @Bean
    public MappingEncryptAndDecryptHttpMessageConverter mappingEncryptAndDecryptHttpMessageConverter() {
        MappingEncryptAndDecryptHttpMessageConverter converter = null;
        if (objectMapper != null) {
            converter = new MappingEncryptAndDecryptHttpMessageConverter(objectMapper);
        } else {
            converter = new MappingEncryptAndDecryptHttpMessageConverter();
        }

        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setEncryptConverter(encryptConverter);
        return converter;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(mappingEncryptAndDecryptHttpMessageConverter());
            }
        };
    }
}