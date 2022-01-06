/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扫描包路径，参考 @{@link org.springframework.context.annotation.ComponentScan}
 *
 * @author maoyz0621 on 2021/11/3
 * @version v1.0
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(CryptClassRegister.class)
public @interface CryptClassScan {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};
}