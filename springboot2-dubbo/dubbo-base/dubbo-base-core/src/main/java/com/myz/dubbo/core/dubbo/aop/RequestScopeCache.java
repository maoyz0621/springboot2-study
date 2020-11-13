/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 15:43  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.aop;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maoyz
 */
@Component
@Scope(
        proxyMode = ScopedProxyMode.TARGET_CLASS,
        value = "request"
)
public class RequestScopeCache {
    public static final Object NONE = new Object();
    private final Map<InvocationContext, Object> cache = new ConcurrentHashMap();

    public RequestScopeCache() {
    }

    public Object get(InvocationContext invocationContext) {
        return this.cache.containsKey(invocationContext) ? this.cache.get(invocationContext) : NONE;
    }

    public void put(InvocationContext methodInvocation, Object result) {
        this.cache.put(methodInvocation, result);
    }
}