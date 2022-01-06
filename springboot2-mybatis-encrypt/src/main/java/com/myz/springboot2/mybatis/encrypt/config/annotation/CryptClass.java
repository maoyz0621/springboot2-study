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
 * 加解密类
 *
 * @author maoyz0621 on 2020/11/2
 * @version v1.0
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptClass {

    /**
     * 加解密的属性值
     *
     * @return
     */
    String[] filed() default {};

    /**
     * 是否开启
     *
     * @return
     */
    boolean open() default true;


    public enum CryptTypeEnum {
        PHONE,
        CARD;
    }
}