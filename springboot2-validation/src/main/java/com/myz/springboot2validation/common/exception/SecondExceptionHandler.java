/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AbstractHandlerExceptionResolver处理异常, 一般用于页面较多, 如果处理application/json, 就要使用response
 *
 * @author maoyz0621 on 19-1-11
 * @version: v1.0
 */
@Slf4j
@Component
public class SecondExceptionHandler extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.debug("SecondExceptionHandler doResolveException()");
        return null;
    }

}
