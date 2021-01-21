/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 18:15  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.filter;


import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

import javax.validation.ConstraintViolationException;

/**
 * @author maoyz
 */
public class ParamExceptionFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ParamExceptionFilter.class);

    public ParamExceptionFilter() {
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;

        try {
            result = invoker.invoke(invocation);
            return result;
        } catch (RuntimeException var8) {
            logger.error(var8.getMessage(), var8);
            if (GenericService.class != invoker.getInterface() && var8.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException)var8.getCause();
                com.myz.springboot2.common.data.Result resultVo = new com.myz.springboot2.common.data.Result();
                resultVo.setStatus(ResultStatusEnum.FAIL.getStatus());
                ConstraintViolation violation = (ConstraintViolation)exception.getConstraintViolations().iterator().next();
                resultVo.setMsg(violation.getPropertyPath() + violation.getMessage());
                resultVo.setData(violation.getPropertyPath().toString());
                // return new RpcResult(resultVo);
                return new AppResponse(null);
            } else {
                throw var8;
            }
        }
    }
}
