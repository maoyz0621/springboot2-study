/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2value.config.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author maoyz0621 on 2023/7/4
 * @version v1.0
 */
@Component
@ConfigurationProperties(prefix = SecurityConfig.PREX, ignoreInvalidFields = true)
@PropertySource("classpath:mask.properties")
public class SecurityConfig {

    public static final String PREX = "mysql";

    @Value("${mysql.mask.config}")
    private String securityMaskConfig;

    private volatile boolean flag;

    private List<SecurityMaskConfigModel> modelList;

    public String getSecurityMaskConfig() {
        return securityMaskConfig;
    }

    public void setSecurityMaskConfig(String securityMaskConfig) {
        this.securityMaskConfig = securityMaskConfig;
    }

    public List<SecurityMaskConfigModel> getMaskConfig() {
        if (flag) {
            return modelList;
        }
        return modelList = get0();
    }

    private List<SecurityMaskConfigModel> get0() {
        List<SecurityMaskConfigModel> convert = SecurityMaskConfigModel.convert(this.securityMaskConfig);
        flag = true;
        return convert;
    }
}