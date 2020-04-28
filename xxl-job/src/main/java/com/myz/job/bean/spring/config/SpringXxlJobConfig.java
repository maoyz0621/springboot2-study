/**
 * Copyright 2020 Inc.
 **/
package com.myz.job.bean.spring.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoyz0621 on 20-4-26
 * @version v1.0
 */
@Configuration
@EnableConfigurationProperties(SpringXxlJobConfigProperties.class)
public class SpringXxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(SpringXxlJobConfig.class);

    private final SpringXxlJobConfigProperties xxlJobConfigProperties;

    public SpringXxlJobConfig(SpringXxlJobConfigProperties xxlJobConfigProperties) {
        this.xxlJobConfigProperties = xxlJobConfigProperties;
    }

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor() {
        logger.info(">>>>>>>>>>>>>>>>> SpringXxlJobConfig config init");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobConfigProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAccessToken(xxlJobConfigProperties.getAccessToken());
        xxlJobSpringExecutor.setAppname(xxlJobConfigProperties.getAppname());
        xxlJobSpringExecutor.setAddress(xxlJobConfigProperties.getAddress());
        xxlJobSpringExecutor.setIp(xxlJobConfigProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobConfigProperties.getPort());
        xxlJobSpringExecutor.setLogPath(xxlJobConfigProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobConfigProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
