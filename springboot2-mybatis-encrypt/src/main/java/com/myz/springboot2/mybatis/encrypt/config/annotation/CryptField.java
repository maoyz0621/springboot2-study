/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加解密字段
 *
 * @author maoyz0621 on 2020/11/2
 * @version v1.0
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptField {

    CryptClass.CryptTypeEnum value() default CryptClass.CryptTypeEnum.PHONE;

    boolean open() default true;
}