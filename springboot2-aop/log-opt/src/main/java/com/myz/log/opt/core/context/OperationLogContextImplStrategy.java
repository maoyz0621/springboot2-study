/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.context;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public interface OperationLogContextImplStrategy {

    OperationLogContext clearContext();

    OperationLogContext getContext();

    OperationLogContext setContext(OperationLogContext context);
}