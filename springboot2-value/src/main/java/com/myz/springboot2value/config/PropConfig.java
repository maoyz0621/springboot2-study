/*
 * Copyright (C) 2019, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-04-30 16:10  Inc. All rights reserved.
 */
package com.myz.springboot2value.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author maoyz
 */
@Configuration
public class PropConfig {

    // 将yml中的内容放入，application.yml文件中正常，自定义myz.yml文件中无法找到。
    // 使用@ConfigurationProperties注解，只能用于properties文件。
    // 解决方式：可以通过PropertySourcePlaceholderConfigurer来加载yml文件，暴露yml文件到spring environment，如下：
    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("myz.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}