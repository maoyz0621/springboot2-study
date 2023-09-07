/**
 * Copyright 2023 Inc.
 **/
package com.myz.commandline.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author maoyz0621 on 2023/5/19
 * @version v1.0
 */
@Component
public class BeanInitCostBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanInitCostBeanPostProcessor.class);

    private static ConcurrentMap<String, Long> BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        BEAN_MAP.put(beanName, System.currentTimeMillis());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (BEAN_MAP.get(beanName) != null) {
            LOGGER.info("beanName:{}, cost={}", beanName, System.currentTimeMillis() - BEAN_MAP.get(beanName));
        }
        return bean;
    }
}