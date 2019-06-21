/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.stereotype.Component;


/**
 * 通过实现AsyncConfigurer自定义异常线程池，包含异常处理
 *
 * @author maoyz0621 on 19-1-6
 * @version: v1.0
 */
@Component
public class MyAsyncConfigurer implements AsyncConfigurer {
    private static final Logger log = LoggerFactory.getLogger(MyAsyncConfigurer.class);

    /**
     * 自定义异常处理类
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return (throwable, method, objects) -> {
            log.info("Exception message - " + throwable.getMessage());
            log.info("Method name - " + method.getName());
            for (Object param : objects) {
                log.info("Parameter value - " + param);
            }
        };
    }
}
