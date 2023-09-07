/*
 * Copyright (C) 2019, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-04-30 12:01  Inc. All rights reserved.
 */
package com.myz.springboot2value.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * 自定义yml文件
 * 建议：分模块
 * https://www.jianshu.com/p/7f75936b573b
 * <p>
 * 当我们为属性配置错误的值时，而又不希望 Spring Boot 应用启动失败，我们可以设置 ignoreInvalidFields 属性为 true (默认为 false)
 * 参数检验 @Validated 同时可以检验属性内容
 * <p>
 * configuration processor 创建 \META-INF\spring-configuration-metadata.json
 *
 * @author maoyz
 */
@Data
@Component
@ConfigurationProperties(prefix = MyProp.PREX, ignoreInvalidFields = true)
@PropertySource("classpath:myz.yml")
public class MyProp {
    public static final String PREX = "props";

    private Integer age;

    // boolean类型
    private boolean enabled;

    // 参数校验
    @NotEmpty
    private String a1;

    // durations (持续时间) 配置 duration 不写单位，默认按照毫秒来指定，我们也可已通过 @DurationUnit 来指定单位:
    private Duration duration;

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration duration0;

    // 字节大小
    private DataSize size;

    @DataSizeUnit(DataUnit.BYTES)
    private DataSize maxSize;

    // 过期
    private String deprecate;

    // list1: 1,2,3
    private List<Integer> list1;

    // list2:
    //     - 1
    //     - 2
    //     - 3
    private List<String> list2;

    // list3:
    //     - a1: a1
    //       b1: b1
    //     - a2: a2
    //       b2: b2
    private List<Map<String, String>> list3;

    // map0:
    //     key1: 'value1'
    //     key2: 'value2'
    private Map<String, String> map0;

    // map1:
    //     key1:
    //       - a1
    //       - a2
    //       - a3
    //     key2:
    //       - b1
    //       - b2
    //       - b3
    private Map<String, List<String>> map1;

    // map2:
    //     key1: {key1: 'value1', key2: 'value2'}
    //     key2:
    //       key1: 'value1'
    //       key2: 'value2'
    private Map<String, Map<String, String>> map2;

    @DeprecatedConfigurationProperty(reason = "change", replacement = "depre")
    public String getDeprecate() {
        return deprecate;
    }

}