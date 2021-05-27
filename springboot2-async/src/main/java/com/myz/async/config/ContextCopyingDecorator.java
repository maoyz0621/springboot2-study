/**
 * Copyright 2021 Inc.
 **/
package com.myz.async.config;

import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 线程上下文copy
 *
 * @author maoyz0621 on 2021/4/18
 * @version v1.0
 */
public class ContextCopyingDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        RequestAttributes context = RequestContextHolder.currentRequestAttributes();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(context);
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }
}