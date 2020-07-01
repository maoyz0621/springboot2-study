/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-07-01 17:34  Inc. All rights reserved.
 */
package com.myz.springboot2validation.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定错误码和错误信息
 *
 * @author maoyz
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionCode {

    // 响应码code
    int value() default 100000;

    // 响应信息msg
    String message() default "参数校验错误";

    ErrorCode code() default ErrorCode.PARAM_ERROR;

}