/**
 * Copyright 2021 Inc.
 **/
package com.myz.encrypt.config.advice;

import com.myz.encrypt.config.converter.AbstractEncryptAndDecryptHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Slf4j
@RestControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {

    /**
     * 返回false,不执行Advice的业务
     *
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("DecryptRequestBodyAdvice.supports");
        Parameter parameter = methodParameter.getParameter();
        return (AbstractEncryptAndDecryptHttpMessageConverter.class.isAssignableFrom(converterType) &&
                methodParameter.getParameterAnnotation(RequestBody.class) != null);
    }

    /**
     * 读取参数前操作
     *
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        log.info("DecryptRequestBodyAdvice.beforeBodyRead");
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    /**
     * 读取参数后执行
     *
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("DecryptRequestBodyAdvice.afterBodyRead");
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    /**
     * 无请求时的处理
     *
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }
}