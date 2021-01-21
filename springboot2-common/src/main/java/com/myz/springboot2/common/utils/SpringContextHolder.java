/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author maoyz0621 on 19-3-5
 * @version v1.0
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 发布事件
     */
    public static void eventPublish(Object event) {
        applicationContext.publishEvent(event);
    }

    /**
     * 取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取指定类型的Bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    /**
     * 获取当前profile,默认获取第一个
     */
    public static String getActiveProfile() {
        Environment environment = applicationContext.getEnvironment();
        return environment.getActiveProfiles().length == 0 ?
                environment.getDefaultProfiles()[0] : environment.getActiveProfiles()[0];
    }

    /**
     * 获取指定注解的Bean
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annType) {
        return applicationContext.getBeansWithAnnotation(annType);
    }

    /**
     * 从Spring容器中获取指定类型的Bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }
}
