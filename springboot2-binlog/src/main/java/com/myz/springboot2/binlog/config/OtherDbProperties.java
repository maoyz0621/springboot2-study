/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author maoyz0621 on 2021/10/7
 * @version v1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "binlog", ignoreInvalidFields = true)
public class OtherDbProperties {

    @Value("${databases}")
    private List<String> databases;
}