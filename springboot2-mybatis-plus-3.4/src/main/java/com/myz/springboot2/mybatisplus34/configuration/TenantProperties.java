/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatisplus34.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maoyz0621 on 2021/12/8
 * @version v1.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "meta.tenant")
public class TenantProperties {

    private Boolean enabled = true;
    private String tenantColumn = "tenant_id";
    // 忽略的表
    private List<String> ignoreTables = Arrays.asList("");
    // 忽略的SQL语句
    private List<String> ignoreSqls = new ArrayList<>();
}