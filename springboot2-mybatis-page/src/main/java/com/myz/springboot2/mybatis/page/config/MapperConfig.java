/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.config;

import com.myz.springboot2.mybatis.page.config.plugin.EncryptDecryptInterceptor;
import com.myz.springboot2.mybatis.page.config.plugin.ParameterInterceptor;
import com.myz.springboot2.mybatis.page.config.plugin.ResultInterceptor;

/**
 * @author maoyz0621 on 2020/11/6
 * @version v1.0
 */
// @Configuration
public class MapperConfig {

    // @Bean
    public ParameterInterceptor parameterInterceptor() {
        return new ParameterInterceptor();
    }

    // @Bean
    public ResultInterceptor resultInterceptor() {
        return new ResultInterceptor();
    }

    // @Bean
    public EncryptDecryptInterceptor encryptDecryptInterceptor() {
        return new EncryptDecryptInterceptor();
    }

    // @Bean
    // public ConfigurationCustomizer configurationCustomizer() {
    //     return configuration -> {
    //         configuration.addInterceptor(parameterInterceptor());
    //         configuration.addInterceptor(resultInterceptor());
    //         configuration.addInterceptor(encryptDecryptInterceptor());
    //     };
    // }
}