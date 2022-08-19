/**
 * Copyright 2022 Inc.
 **/
package com.myz.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.annotations.Mask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author maoyz0621 on 2022/8/19
 * @version v1.0
 */
@RestControllerAdvice
public class ConventResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConventResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String name = returnType.getMethod().getName();
        LOGGER.info("ResponseBodyAdvice supports MethodParameter={}", name);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        LOGGER.info("ResponseBodyAdvice beforeBodyWrite body={}", body);

        Mask mask = returnType.getMethodAnnotation(Mask.class);
        LOGGER.info("ResponseBodyAdvice beforeBodyWrite methodAnnotations={}", mask);

        ObjectMapper objectMapper = new ObjectMapper();
        if (Objects.equals(body, "1")) {
            Map<String, Object> val = new HashMap<>();
            val.put("sign", "abc");
            try {
                return objectMapper.writeValueAsString(val);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if (returnType.getGenericParameterType().equals(String.class)) {

            Map<String, Object> val = new HashMap<>();
            val.put("sign", "abc123");
            try {
                return objectMapper.writeValueAsString(val);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}