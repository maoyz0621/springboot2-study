/**
 * Copyright 2021 Inc.
 **/
package com.myz.encrypt.config.advice;

import com.myz.encrypt.config.annotation.EncryptResponse;
import com.myz.encrypt.config.converter.AbstractEncryptAndDecryptHttpMessageConverter;
import com.myz.encrypt.config.converter.EncryptResponseContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ResponseBodyAdvice 对请求响应结果的处理
 *
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Slf4j
@RestControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("EncryptResponseBodyAdvice.supports");
        return (AbstractEncryptAndDecryptHttpMessageConverter.class.isAssignableFrom(converterType))
                && returnType.hasMethodAnnotation(EncryptResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("EncryptResponseBodyAdvice.beforeBodyWrite");
        if (body == null) {
            return null;
        }

        EncryptResponseContainer container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, selectedContentType, returnType, request, response);
        return container;
    }

    private EncryptResponseContainer getOrCreateContainer(Object body) {
        return (body instanceof EncryptResponseContainer ? (EncryptResponseContainer) body : new EncryptResponseContainer(body));

    }

    protected void beforeBodyWriteInternal(EncryptResponseContainer container, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        EncryptResponse encryptResponse = returnType.getMethodAnnotation(EncryptResponse.class);
        container.setEncryptResponse(encryptResponse);
    }
}