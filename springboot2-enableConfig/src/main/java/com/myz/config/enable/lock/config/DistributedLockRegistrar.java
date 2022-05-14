/**
 * Copyright 2022 Inc.
 **/
package com.myz.config.enable.lock.config;


import com.myz.config.enable.lock.DistributedLockAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @author maoyz0621 on 2022/5/13
 * @version v1.0
 */
public class DistributedLockRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        LOGGER.info("======================= DistributedLockRegistrar registerBeanDefinitions =======================");
        Class<DistributedLockAspect> lockAspectClass = DistributedLockAspect.class;
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(lockAspectClass);
        String beanName = StringUtils.uncapitalize(lockAspectClass.getSimpleName());
        // 注册Bean
        registry.registerBeanDefinition(beanName, rootBeanDefinition);
    }
}