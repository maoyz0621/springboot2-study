/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.config.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，将日期字符串转成long型
 *
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface DateToLongFormat {

    String pattern() default "";
}