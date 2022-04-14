/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2validation.utils;

import com.alibaba.fastjson.JSONObject;
import com.myz.springboot2validation.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * 验证校验器
 *
 * @author maoyz0621 on 19-6-21
 * @version v1.0
 */
public class ValidatorUtils {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Result validator(T t) {
        return validator(t, Default.class);
    }

    public static <T> Result validator(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
        if (constraintViolations.isEmpty()) {
            // TODO 可以优化，返回成功
            Result success = new Result();
            success.setCode(10000);
            success.setSuccess(true);
            return success;
        }

        StringBuilder sb = new StringBuilder();
        // constraintViolation.getPropertyPath()获取属性名, constraintViolation.getMessage()获取错误信息
        constraintViolations.forEach((constraintViolation) ->
                sb.append(constraintViolation.getPropertyPath())
                        .append(":")
                        .append(constraintViolation.getMessage())
                        .append(";"));

        logger.error("************************ {} *************************", sb);
        // TODO 返回错误信息
        Result errorResult = new Result();
        errorResult.setCode(10000);
        errorResult.setMessage(JSONObject.toJSONString(sb));
        errorResult.setSuccess(false);
        return errorResult;
    }
}
