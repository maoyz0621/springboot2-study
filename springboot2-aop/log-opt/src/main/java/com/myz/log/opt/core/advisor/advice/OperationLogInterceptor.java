/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.advisor.advice;

import com.myz.log.opt.core.context.OperationLogContext;
import com.myz.log.opt.core.support.MethodInvocationOperationLogCallback;
import com.myz.log.opt.core.support.OperationLogCallback;
import com.myz.log.opt.core.support.OperationLogOperations;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public final class OperationLogInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogInterceptor.class);

    private final OperationLogOperations operationLogOperations;

    public OperationLogInterceptor(OperationLogOperations operationLogOperations) {
        this.operationLogOperations = operationLogOperations;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        OperationLogCallback<Object, Throwable> operationLogCallback =
                new MethodInvocationOperationLogCallback<Object, Throwable>(invocation) {
                    @Override
                    public Object doWithOperationLog(OperationLogContext context) throws Throwable {
                        if (logger.isDebugEnabled()) {
                            logger.debug("0={======> {} <======}=0", context);
                        }
                        return invocation.proceed();
                    }
                };

        return this.operationLogOperations.execute(operationLogCallback);
    }
}