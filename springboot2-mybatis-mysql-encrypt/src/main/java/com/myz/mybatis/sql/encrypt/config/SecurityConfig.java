/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2023/7/4
 * @version v1.0
 */
@Component
@ConfigurationProperties(prefix = SecurityConfig.PREX, ignoreInvalidFields = true)
@PropertySource("classpath:mask.properties")
@Data
public class SecurityConfig {

    public static final String PREX = "mysql";

    @Value("${mysql.mask.config}")
    private String securityMaskConfig;

    public SecurityMaskConfigProperty get(){
        String[] split = securityMaskConfig.split("|");
        System.out.println(split);
        return new SecurityMaskConfigProperty();
    }
}