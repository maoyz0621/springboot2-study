/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.validate;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 是否开启验证功能
 * @author maoyz0621 on 2021/2/4
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidateImportSelector.class)
public @interface EnableValidate {

    String policy() default "simple";

    Class policyClass() default ValidateService.class;

    /**
     * 是否开启功能
     * @return
     */
    boolean isOpen() default true;

    int order() default Integer.MAX_VALUE;
}