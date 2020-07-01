/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过实现异常捕获接口,处理Controller中的异常
 *
 * @author maoyz0621 on 19-1-11
 * @version v1.0
 */
@Component
public class BaseExceptionHandler implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.debug("BaseExceptionHandler resolveException()");
        return null;
    }
}
