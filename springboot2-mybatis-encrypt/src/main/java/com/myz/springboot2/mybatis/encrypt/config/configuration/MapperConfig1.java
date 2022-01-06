/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.configuration;

import com.myz.springboot2.mybatis.encrypt.config.plugin.CryptInterceptor;
import com.myz.springboot2.mybatis.encrypt.config.plugin.ParameterInterceptor;
import com.myz.springboot2.mybatis.encrypt.config.plugin.ResultInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author maoyz0621 on 2020/11/6
 * @version v1.0
 */
// @Configuration
public class MapperConfig1 {

    @Bean
    public ParameterInterceptor parameterInterceptor() {
        return new ParameterInterceptor();
    }

    // @Bean
    public ResultInterceptor resultInterceptor() {
        return new ResultInterceptor();
    }

    @Bean
    public CryptInterceptor encryptDecryptInterceptor() {
        return new CryptInterceptor();
    }

}