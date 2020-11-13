/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-07 15:34  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;

/**
 * @author maoyz
 */
@Activate(group = {"PROVIDER"})
public class LocaleProviderFilter implements Filter {
    private static final String LOCALE = "locale";

    public LocaleProviderFilter() {
    }
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String localeStr = "";
        Locale locale = LocaleContextHolder.getLocale();
        if (Objects.nonNull(locale)) {
            localeStr = locale.toString();
        }

        RpcContext.getContext().setAttachment("locale", localeStr);
        return invoker.invoke(invocation);
    }
}