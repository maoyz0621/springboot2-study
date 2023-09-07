/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author maoyz0621 on 2023/7/4
 * @version v1.0
 */
@Configuration
public class PropConfig {

    // 将yml中的内容放入，application.yml文件中正常，自定义myz.yml文件中无法找到。
    // 使用@ConfigurationProperties注解，只能用于properties文件。
    // 解决方式：可以通过PropertySourcePlaceholderConfigurer来加载yml文件，暴露yml文件到spring environment，如下：
    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        PropertiesFactoryBean yaml = new PropertiesFactoryBean();
        yaml.setLocation(new ClassPathResource("mask.properties"));
        return configurer;
    }

}