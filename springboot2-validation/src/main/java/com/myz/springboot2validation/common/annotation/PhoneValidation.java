/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义检验注解
 *
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@Documented
// 指定真正实现校验规则的类
@Constraint(validatedBy = PhoneValidationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneValidation {

    String message() default "请输入正确的手机号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PhoneValidation[] value();
    }

}
