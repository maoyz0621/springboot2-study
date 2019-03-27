/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2value.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 引入外部配置文件@PropertySource({"classpath:web.properties"})
 *
 * @author maoyz0621 on 19-1-8
 * @version: v1.0
 */
@Component
public class IndexService {

    @Value("${web.path}")
    private String path;

    public String getPath() {
        return path;
    }
}
