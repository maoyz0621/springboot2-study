/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.i18n.config;

import com.myz.springboot2.common.data.Result;
import com.myz.springboot2.common.exception.BaseException;
import com.myz.springboot2.common.exception.BusinessException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://juejin.im/post/5ed7a03f518825433c13ae47#heading-2
 *
 * @author maoyz0621 on 20-4-21
 * @version v1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常，满足国际化处理
     * 返回的status:@ResponseStatus(HttpStatus.OK)
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException ex) {
        String message = I18nMessageUtil.getMessage(ex.getMessage(), LocaleContextHolder.getLocale());
        if (StringUtils.isEmpty(message)) {
            return Result.buildFailure();
        }
        return Result.buildFailure(1000, message);
    }

    /**
     * 自定义异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException ex) {
        return Result.buildFailure(ex.getErrorCodeI().getCode(), ex.getErrorCodeI().getMessage());
    }

    /**
     * 进入Controller前的异常
     * NoHandlerFoundException：首先根据请求Url查找有没有对应的控制器，若没有则会抛该异常，也就是大家非常熟悉的404异常
     * HttpRequestMethodNotSupportedException 匹配GET POST
     * HttpMediaTypeNotSupportedException 对请求头与控制器支持的做比较，比如content-type请求头，若控制器的参数签名包含注解@RequestBody，但是请求的content-type请求头的值没有包含application/json，那么会抛该异常
     * MissingPathVariableException 未检测到路径参数。比如url为：/licence/{licenceId}，参数签名包含@PathVariable("licenceId")，当请求的url为/licence，在没有明确定义url为/licence的情况下，会被判定为：缺少路径参数
     * MissingServletRequestParameterException 缺少请求参数。比如定义了参数@RequestParam("id") String id，但发起请求时，未携带该参数，则会抛该异常
     * TypeMismatchException 参数类型匹配失败。比如：接收参数为Long型，但传入的值确是一个字符串，那么将会出现类型转换失败的情况，这时会抛该异常
     * HttpMessageNotReadableException  与上面的HttpMediaTypeNotSupportedException举的例子完全相反，即请求头携带了"content-type: application/json;charset=UTF-8"，但接收参数却没有添加注解@RequestBody，或者请求体携带的 json 串反序列化成 pojo 的过程中失败了，也会抛该异常
     * HttpMessageNotWritableException 返回的 pojo 在序列化成 json 过程失败了，那么抛该异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Result handleServletException(Exception ex) {
        return Result.buildFailure();
    }


    /**
     * 当入参是form表单形式时，参数绑定异常，抛出BindException，处理BindException异常，@Validated 和 @Valid
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public Result handleBindException(final BindException ex) {
        String errorMsgs = ex.getBindingResult().getFieldErrors().stream().map(
                param -> {
                    return "参数:" + param.getField() + ",值[" + param.getRejectedValue() + "]:" + param.getDefaultMessage();
                }).collect(Collectors.joining("; "));

        return Result.buildFailure(1000, errorMsgs);
    }

    /**
     * 当入参是json时，参数校验异常，抛出MethodArgumentNotValidException，处理BindException异常，@Validated 和 @Valid
     * 提示其中一个错误，并非所有参数错误信息
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleException(MethodArgumentNotValidException ex) throws NoSuchFieldException {
        Result errorResult = new Result();
        String field = ex.getBindingResult().getFieldErrors().get(0).getField();
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        errorResult.setCode(200001);
        errorResult.setMessage(field + " : " + message);

        annoError(ex, errorResult);

        return errorResult;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(ConstraintViolationException ex) {
        Result errorResult = new Result();

        handleConstraintViolationException(ex, errorResult);

        return Result.buildFailure();
    }

    /**
     * 未定义异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        Result errorResult = new Result();
        if (ex instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) ex, errorResult);
        } else {
            errorResult.setMessage(ex.getMessage());
        }

        return errorResult;
    }

    private void annoError(MethodArgumentNotValidException ex, Result errorResult) throws NoSuchFieldException {
        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = ex.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = ex.getBindingResult().getFieldErrors().get(0).getField();
        Field field0 = parameterType.getDeclaredField(fieldName);
        // ExceptionCode annotation = field0.getAnnotation(ExceptionCode.class);
        // if (annotation != null) {
        //     errorResult.setCode(annotation.value());
        //     errorResult.setMessage(annotation.message());
        // }
    }

    /**
     * ConstraintViolationException
     *
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

        // 生成返回结果
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;
    }
}