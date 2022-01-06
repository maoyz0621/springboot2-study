/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.annotation;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 参考@{@link MapperScannerRegistrar}
 *
 * @author maoyz0621 on 2021/11/3
 * @version v1.0
 */
@Slf4j
public class CryptClassRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {

    private static Map<String, CryptClass> map = new ConcurrentHashMap<>(256);

    @Nullable
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private Environment environment;

    private ResourceLoader resourceLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes cryptClassScanAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(CryptClassScan.class.getName())
        );

        if (cryptClassScanAttrs != null) {
            registerBeanDefinitions(importingClassMetadata, cryptClassScanAttrs, registry, generateBaseBeanName(importingClassMetadata, 0));
        }
        // Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(CryptClassScan.class.getName());
        // log.info("{}", annotationAttributes);
        //
        // ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider() {
        //
        // };
        //
        // classPathScanningCandidateComponentProvider.setResourceLoader(this.resourceLoader);
        //
        // AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(CryptClass.class);
        // classPathScanningCandidateComponentProvider.addIncludeFilter(annotationTypeFilter);

    }

    void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);

        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(
                Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));
        basePackages.addAll(
                Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList()));

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));

        }
        builder.addPropertyValue("basePackages", StringUtils.collectionToCommaDelimitedString(basePackages));
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + CryptClassRegister.class.getSimpleName() + "#" + index;
    }


}