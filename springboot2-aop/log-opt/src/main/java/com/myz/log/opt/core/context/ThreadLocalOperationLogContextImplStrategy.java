/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.context;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class ThreadLocalOperationLogContextImplStrategy implements OperationLogContextImplStrategy {

    private static final ThreadLocal<OperationLogContext> CONTEXT = new InheritableThreadLocal<>();

    @Override
    public OperationLogContext clearContext() {
        OperationLogContext value = getContext();
        OperationLogContext parent = value == null ? null : value.getParent();
        CONTEXT.set(parent);
        return value;
    }

    @Override
    public OperationLogContext getContext() {
        return CONTEXT.get();
    }

    @Override
    public OperationLogContext setContext(OperationLogContext context) {
        OperationLogContext oldContext = getContext();
        CONTEXT.set(context);
        return oldContext;
    }
}