/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.model;

import com.myz.log.opt.core.support.MethodInvocationOperationLogCallback;
import com.myz.log.opt.core.support.OperationLogCallback;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class MethodInvocationResult {

    private MethodInvocation methodInvocation;

    private Object result;

    private Throwable throwable;

    private StopWatch performance;

    public MethodInvocationResult(MethodInvocation methodInvocation, Object result, Throwable throwable) {
        this.methodInvocation = methodInvocation;
        this.result = result;
        this.throwable = throwable;
    }

    public MethodInvocationResult(MethodInvocation methodInvocation, Object result, Throwable throwable, StopWatch performance) {
        this(methodInvocation, result, throwable);
        this.performance = performance;
    }

    public MethodInvocationResult(OperationLogCallback<?, ?> operationLogCallback, Object result, Throwable throwable, StopWatch performance) {
        this(
                operationLogCallback instanceof MethodInvocationOperationLogCallback ?
                        ((MethodInvocationOperationLogCallback<?, ?>) operationLogCallback).getInvocation() : null,
                result,
                throwable
        );
        this.performance = performance;
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public StopWatch getPerformance() {
        return performance;
    }

    public void setPerformance(StopWatch performance) {
        this.performance = performance;
    }
}