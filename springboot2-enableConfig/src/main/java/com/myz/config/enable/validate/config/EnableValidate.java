/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.validate.config;

import com.myz.config.enable.validate.ValidateService;
import com.myz.config.enable.validate.ValidateType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否开启验证功能
 *
 * @author maoyz0621 on 2021/2/4
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidateTypeImportSelector.class)
public @interface EnableValidate {

    /**
     * 策略
     */
    ValidateType policy() default ValidateType.SIMPLE;

    Class<? extends ValidateService> policyClass() default ValidateService.class;

    /**
     * 是否开启功能
     */
    boolean isOpen() default true;

    int order() default Integer.MAX_VALUE;
}