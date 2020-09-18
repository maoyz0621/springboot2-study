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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest异常捕获，处理数据检验validation异常
 *
 * @author maoyz0621 on 19-1-11
 * @version v1.0
 */
@ControllerAdvice
public class BindExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BindExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex) {
        Result errorResult = new Result();
        errorResult.setMessage(ex.getMessage());
        return errorResult;
    }

    /**
     * 当入参是form表单形式时，参数绑定异常，抛出BindException，处理BindException异常，@Validated 和 @Valid
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result handleException(BindException ex) {
        Result errorResult = new Result();
        return handleBindException(ex, errorResult);
    }

    /**
     * 当入参是json时，参数校验异常，抛出MethodArgumentNotValidException，处理BindException异常，@Validated 和 @Valid
     * 提示其中一个错误，并非所有参数错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleException(MethodArgumentNotValidException ex) {
        Result errorResult = new Result();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        errorResult.setCode(200001);
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            sb.append(field).append(":").append(message).append(";");
        }
        errorResult.setMessage(sb.toString());
        // analysisError(ex, errorResult);
        return errorResult;
    }

    // private void analysisError(MethodArgumentNotValidException ex, Result errorResult) throws NoSuchFieldException {
    //     // 参数的Class对象，等下好通过字段名称获取Field对象
    //     Class<?> parameterType = ex.getParameter().getParameterType();
    //     // 拿到错误的字段名称
    //     String fieldName = ex.getBindingResult().getFieldErrors().get(0).getField();
    //
    //     Field[] fields = parameterType.getDeclaredFields();
    //     for (Field field : fields) {
    //         System.out.println(field.getName());
    //         ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);
    //         if (annotation != null) {
    //             errorResult.setCode(annotation.value());
    //             errorResult.setMessage(annotation.message());
    //         }
    //     }
    //     // Field field0 = parameterType.getDeclaredField(fieldName);
    //
    // }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result handleException(ConstraintViolationException ex) {
        return handleConstraintViolationException(ex);
    }

    /**
     * ConstraintViolationException -> @Valid
     *
     * @param ex
     * @return
     */
    private Result handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> list = ex.getConstraintViolations().stream()
                .map(violation -> {
                    return "参数非法:" +
                            violation.getPropertyPath().toString().split("[.]")[1]
                            + violation.getMessage();
                })
                .collect(Collectors.toList());
        Result errorResult = new Result();
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

        logger.error("数据校验错误量 = {} , 错误信息[{}]", bindingResult.getErrorCount(), sb);

        // 生成返回结果
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }

}
