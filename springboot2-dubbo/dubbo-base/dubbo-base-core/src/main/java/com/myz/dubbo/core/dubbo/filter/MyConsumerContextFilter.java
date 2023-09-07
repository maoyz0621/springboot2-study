/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 16:31  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.filter;

import com.myz.dubbo.core.UserContext;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;

import static org.apache.dubbo.common.constants.CommonConstants.APPLICATION_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.REMOTE_APPLICATION_KEY;

/**
 * @author maoyz
 */
@Activate(group = {CommonConstants.CONSUMER})
public class MyConsumerContextFilter implements Filter {
    private static  final String USER_INFO = "userInfo";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        UserContext userContext = new UserContext().setUserName("maoyz");
        RpcContext.getContext()
                .setInvoker(invoker)
                .setInvocation(invocation)
                .setLocalAddress(NetUtils.getLocalHost(),0)
                .setRemoteAddress(invoker.getUrl().getHost(),invoker.getUrl().getPort())
                .setRemoteApplicationName(invoker.getUrl().getParameter(REMOTE_APPLICATION_KEY))
                .setAttachment(REMOTE_APPLICATION_KEY, invoker.getUrl().getParameter(APPLICATION_KEY))
                .setAttachment(USER_INFO, userContext)
        ;

        invocation.getAttachments().put(USER_INFO, userContext.toString());

        if (invocation instanceof RpcInvocation) {
            ((RpcInvocation) invocation).setInvoker(invoker);
        }

        try {
            return invoker.invoke(invocation);
        } finally {
            String attachment = RpcContext.getContext().getAttachment("");
            RpcContext.getContext().clearAttachments();
        }
    }
}