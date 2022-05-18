/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.annotation;

import com.myz.distribute.lock.spring.aspect.DistributedLockAspect;
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

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Class<DistributedLockAspect> lockAspectClass = DistributedLockAspect.class;
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(lockAspectClass);
        String beanName = StringUtils.uncapitalize(lockAspectClass.getSimpleName());
        registry.registerBeanDefinition(beanName, rootBeanDefinition);
    }
}