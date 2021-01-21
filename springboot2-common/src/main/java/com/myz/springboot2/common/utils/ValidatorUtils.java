/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.common.utils;


import com.myz.springboot2.common.data.Result;
import com.myz.springboot2.common.data.ResultStatusEnum;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 验证校验器
 *
 * @author maoyz0621 on 19-6-21
 * @version v1.0
 */
public class ValidatorUtils {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);

    private static MessageSource validationMessageSource = SpringContextHolder.getBean("validationMessageSource", MessageSource.class);

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static Validator i18nValidator = Validation.byDefaultProvider().configure()
            .messageInterpolator(new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(validationMessageSource)))
            .buildValidatorFactory()
            .getValidator();

    public static <T> Result<T> validator(T t) {
        // Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        Set<ConstraintViolation<T>> constraintViolations = i18nValidator.validate(t);
        if (constraintViolations.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        // constraintViolation.getPropertyPath()获取属性名, constraintViolation.getMessage()获取错误信息
        constraintViolations.forEach((constraintViolation) ->
                sb.append(constraintViolation.getPropertyPath())
                        .append(" : ")
                        .append(constraintViolation.getMessage())
                        .append(" ;"));

        logger.error("************************ {} *************************", sb);
        return Result.fail(ResultStatusEnum.PARAM_ERR.getStatus(),sb.toString());
    }
}
