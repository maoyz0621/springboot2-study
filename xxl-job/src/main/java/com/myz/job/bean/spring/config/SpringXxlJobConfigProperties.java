/**
 * Copyright 2020 Inc.
 **/
package com.myz.job.bean.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author maoyz0621 on 20-4-26
 * @version v1.0
 */
@Data
@ConfigurationProperties(prefix = SpringXxlJobConfigProperties.PREFIX)
public class SpringXxlJobConfigProperties {
    public static final String PREFIX = "xxl.job.executor";

    private String adminAddresses;
    private String accessToken;
    private String appname;
    private String address;
    private String ip;
    private int port;
    private String logPath;
    private int logRetentionDays;
}
