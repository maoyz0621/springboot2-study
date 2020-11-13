/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 15:45  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.aop;

import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author maoyz
 */
@Component
@Aspect
@Order(101)
public class MemoizerAspect {
    private static Logger logger = LoggerFactory.getLogger(MemoizerAspect.class);
    @Autowired
    private RequestScopeCache requestScopeCache;

    public MemoizerAspect() {
    }

    @Around("(execution(* com.oppo..*(..))) && @annotation(com.oppo.mkt.spring.boot.core.annotation.Memoize)")
    public Object memoize(ProceedingJoinPoint pjp) throws Throwable {
        InvocationContext invocationContext = new InvocationContext(pjp.getSignature().getDeclaringType(), pjp.getSignature().getName(), pjp.getArgs());
        Object result = this.requestScopeCache.get(invocationContext);
        if (RequestScopeCache.NONE == result) {
            result = pjp.proceed();
            logger.info("Memoizing result {}, for method invocation: {}", result, invocationContext);
            this.requestScopeCache.put(invocationContext, result);
        } else {
            logger.info("Using memoized result: {}, for method invocation: {}", result, invocationContext);
        }

        return result;
    }
}