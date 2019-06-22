/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.exception;

import com.myz.springboot2validation.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest异常捕获，处理数据检验validation异常
 *
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@ControllerAdvice
public class BindExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BindExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex) {
        Result errorResult = new Result();
        if (ex instanceof BindException) {
            return handleBindException((BindException) ex, errorResult);
        } else if (ex instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) ex, errorResult);
        }

        return errorResult;
    }

    /**
     * ConstraintViolationException
     * @param ex
     * @param errorResult
     * @return
     */
    private Result handleConstraintViolationException(ConstraintViolationException ex, Result errorResult) {
        List<String> list = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        errorResult.setCode(1000001);
        errorResult.setMessage(list.toString());
        return errorResult;
    }

    /**
     * BindException专门用来处理数据检验validation异常
     */
    private Result handleBindException(BindException ex, Result errorResult) {
        // ex.getFieldError():随机返回一个对象属性的异常信息
        // 如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()

        // 获取绑定结果
        BindingResult bindingResult = ex.getBindingResult();

        // 方法1
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 方法2
        List<FieldError> allErrors = ex.getFieldErrors();

        StringBuilder sb = new StringBuilder();
        ex.getFieldErrors().forEach((fieldError) -> {
            sb.append(fieldError.getField())
                    .append(" = [")
                    .append(fieldError.getRejectedValue())
                    .append("] : ")
                    .append(fieldError.getDefaultMessage())
                    .append(" ; ");
        });

        logger.debug("*********** 数据校验错误量 = {} , 错误信息[{}]*************", bindingResult.getErrorCount(), sb);

        // 生成返回结果
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }

}
