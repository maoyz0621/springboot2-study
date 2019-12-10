/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author maoyz on 2018/6/29
 * @version: v1.0
 */
@Configuration
public class FreeMarkerConfig {

    /**
     * 构造方法执行后，初始化,@PostConstruct注解的方法将会在依赖注入完成后被自动调用
     */
    @PostConstruct
    public void setFreeMarker() {

    }
}
