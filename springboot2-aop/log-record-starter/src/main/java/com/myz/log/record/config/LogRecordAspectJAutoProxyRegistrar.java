/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.config;

import com.myz.log.record.annotation.EnableLogRecord;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class LogRecordAspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes enableLogRecord = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableLogRecord.class.getName(), false));
        if (enableLogRecord != null) {
            AdviceMode mode = (AdviceMode) enableLogRecord.getOrDefault("mode", AdviceMode.PROXY);
            switch (mode) {
                case PROXY:
                    // 自动根据情况代理，优先JDK代理
                    AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
                    break;
                case ASPECTJ:
                    // cglib 代理
                    AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
            }
        }
    }
}