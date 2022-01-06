/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.configuration;

import com.myz.springboot2.mybatis.encrypt.config.plugin.CryptInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoyz0621 on 2020/11/6
 * @version v1.0
 */
@Configuration
public class MapperConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // configuration.addInterceptor(new ParameterInterceptor());
            // configuration.addInterceptor(new ResultInterceptor());
            configuration.addInterceptor(new CryptInterceptor());
        };
    }
}