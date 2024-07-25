package com.myz.log.opt.core.support;

/**
 * @author maoyz0621
 */
public interface OperationLogOperations {

    <T, E extends Throwable> T execute(OperationLogCallback<T, E> operationLogCallback) throws E;

}
