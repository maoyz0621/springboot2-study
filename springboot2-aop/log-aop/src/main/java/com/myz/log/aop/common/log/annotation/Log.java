/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2018/12/27
 * @version: v1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    String level() default "info";

    String value() default "";

    /**
     * 是否记录传递参数
     */
    boolean printArgument() default true;

    /**
     * 记录执行结果
     */
    boolean printResult() default true;

    /**
     * 如果为true，则类下面的LogEvent启作用，否则忽略
     */
    boolean logEnable() default true;

    /**
     * 是否异步执行,默认为true
     *
     * @return true, 如果需要异步执行
     */
    boolean async() default true;
}
