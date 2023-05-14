/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.annotation;

import com.myz.encrypt.config.enums.EncryptFiledTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2023/5/5
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface EncryptFiled {

    /**
     * 是否嵌套
     *
     * @return
     */
    boolean nesting() default false;

    EncryptFiledTypeEnum type() default EncryptFiledTypeEnum.TO_STRING;
}