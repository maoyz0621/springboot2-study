/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecord {

    /**
     * 操作日志的文本模板
     *
     * @return
     */
    String success();

    /**
     * 操作日志失败的文本版本
     *
     * @return
     */
    String fail() default "";

    /**
     * 操作日志的执行人
     *
     * @return
     */
    String operator() default "";

    /**
     * 操作日志绑定的业务对象标识
     *
     * @return
     */
    String bizNo();

    /**
     * 操作日志的种类
     *
     * @return
     */
    String category() default "";

    /**
     * 扩展参数，记录操作日志的修改详情
     *
     * @return
     */
    String detail() default "";

    /**
     * 是否记录日志的条件，字符串类型boolean值，只能为true/false或TRUE/FALSE <p>
     * <p>
     * 使用示例 ：
     * <pre>
     * condition = "true"
     * condition = "false"
     * condition = "{#func()}"  // func 是自定义函数, 返回 true/false 字符串
     * condition = "{#func(#user.userid)}" // 根据用户的情况判读是否需要记录日志，eg. 仅记录特定角色用户的操作行为
     * </pre>
     */
    String condition() default "";
}