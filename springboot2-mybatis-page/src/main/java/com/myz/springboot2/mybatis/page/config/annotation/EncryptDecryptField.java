/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.page.config.annotation;

import java.lang.annotation.*;

/**
 * 加解密字段
 * @author maoyz0621 on 2020/11/2
 * @version v1.0
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptDecryptField {
}