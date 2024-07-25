package com.myz.log.opt.core.support;


import com.myz.log.opt.core.context.OperationLogContext;


/**
 * @author maoyz0621
 */
public interface OperationLogCallback<T, E extends Throwable> {

    /**
     * Execute an operation with operation log semantics.
     * @param context the current operation log context.
     * @return the result of the successful operation.
     * @throws E of type E if processing fails
     */
    T doWithOperationLog(OperationLogContext context) throws E;

}
