/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 18:15  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.filter;



import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
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
public class ParamExceptionFilter {
    private static Logger logger = LoggerFactory.getLogger(ParamExceptionFilter.class);

    public ParamExceptionFilter() {
    }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;

        try {
            result = invoker.invoke(invocation);
            return result;
        } catch (RuntimeException var8) {
            logger.error(var8.getMessage(), var8);
            if (GenericService.class != invoker.getInterface() && var8.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException)var8.getCause();
                // com.oppo.mkt.utils.network.result.Result resultVo = new com.oppo.mkt.utils.network.result.Result();
                // resultVo.setStatus(ResultStatusEnum.FAIL.getStatus());
                // ConstraintViolation violation = (ConstraintViolation)exception.getConstraintViolations().iterator().next();
                // resultVo.setMsg(violation.getPropertyPath() + violation.getMessage());
                // resultVo.setData(violation.getPropertyPath().toString());
                // return new RpcResult(resultVo);
                return new RpcResult(null);
            } else {
                throw var8;
            }
        }
    }
}
