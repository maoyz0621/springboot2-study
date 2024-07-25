/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.config;

import com.myz.log.opt.annotation.EnableOperationLog;
import com.myz.log.opt.core.support.OperationLogTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import static com.myz.log.opt.config.OpLogProperties.OPLOG_PREFIX;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = OPLOG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(OperationLogTemplate.class)
@EnableConfigurationProperties(OpLogProperties.class)
public class OperationLogAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @EnableOperationLog
    public static class EnableOperationLogConfiguration {
    }
}