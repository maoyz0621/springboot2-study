/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.configuration;

import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptClass;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maoyz0621 on 2021/11/4
 * @version v1.0
 */
@Component
public class AnnotationApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, CryptClass> map = new ConcurrentHashMap<>(256);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 扫描注解到容器中的bean，由于CryptClass注解的Class没有@Componet，所以不会被扫描到
        Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(CryptClass.class);
        System.out.println(beans);
    }
}