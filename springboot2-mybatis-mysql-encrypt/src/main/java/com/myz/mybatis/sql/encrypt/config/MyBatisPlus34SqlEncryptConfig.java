/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config;

import com.myz.mybatis.sql.encrypt.config.interceptor.SqlParserStatementInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoyz0621 on 2023/5/25
 * @version v1.0
 */
@Configuration
@MapperScan("com.myz.mybatis.sql.encrypt")
public class MyBatisPlus34SqlEncryptConfig {

    @Bean
    public SqlParserStatementInterceptor sqlParserStatementInterceptor() {
        return new SqlParserStatementInterceptor();
    }

}