/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.config;

import com.myz.log.record.annotation.EnableLogRecord;
import com.myz.log.record.aspect.LogRecordAspect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "log.opt", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(LogRecordAspect.class)
public class LogRecordProxyAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @EnableLogRecord
    public static class EnableLogRecordConfiguration {
    }

}