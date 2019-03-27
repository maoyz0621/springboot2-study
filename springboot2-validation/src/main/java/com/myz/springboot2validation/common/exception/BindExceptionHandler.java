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

import java.util.List;

/**
 * Rest异常捕获，处理数据检验validation异常
 *
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@ControllerAdvice
public class BindExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BindExceptionHandler.class);

    /**
     * BindException专门用来处理数据检验validation异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result handleBindException(BindException ex) {
        // ex.getFieldError():随机返回一个对象属性的异常信息
        // 如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()

        // 获取绑定结果
        BindingResult bindingResult = ex.getBindingResult();

        logger.debug("数据校验错误量 = {}", bindingResult.getErrorCount());

        // 方法1
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 方法2
        List<FieldError> allErrors = ex.getFieldErrors();
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : allErrors) {
            sb.append(fieldError.getField())
                    .append(" = [")
                    .append(fieldError.getRejectedValue())
                    .append("] ")
                    .append(fieldError.getDefaultMessage())
                    .append(" ; ");
        }

        // 生成返回结果
        Result errorResult = new Result();
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }

}
