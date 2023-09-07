/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 18:15  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.filter;


import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author maoyz
 */
public class ParamExceptionFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ParamExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;

        try {
            result = invoker.invoke(invocation);
            return result;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            if (GenericService.class != invoker.getInterface() && t.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException) t.getCause();
                com.myz.dubbo.core.Result resultVo = new com.myz.dubbo.core.Result();
                ConstraintViolation violation = (ConstraintViolation) exception.getConstraintViolations().iterator().next();
                resultVo.setMessage(violation.getPropertyPath() + violation.getMessage());
                resultVo.setData(violation.getPropertyPath().toString());
                // return new RpcResult(resultVo);
                return new AppResponse(null);
            } else {
                throw t;
            }
        }
    }
}
