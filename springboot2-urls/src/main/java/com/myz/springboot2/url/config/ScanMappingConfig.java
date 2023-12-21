/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.url.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 可以加上@Con
 *
 * @author maoyz0621 on 2023/9/11
 * @version v1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.scan.urls", value = {"enable"}, havingValue = "true")
public class ScanMappingConfig {

    @Autowired
    private UrlMappingManager urlMappingManager;

    @PostConstruct
    public void initUrlMappings() {
        urlMappingManager.initUrlMappings();
    }
}