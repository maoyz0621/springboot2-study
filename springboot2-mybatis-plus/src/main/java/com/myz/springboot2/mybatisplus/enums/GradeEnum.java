/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.ToString;

/**
 * 方法一，使用注解@EnumValue,推荐配置typeEnumsPackage
 *
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
// @ToString
public enum GradeEnum {

    PRIMARY(1, "小学"), SECONDORY(2, "中学"), HIGH(3, "高中");

    @EnumValue
    private final int code;

    private final String descp;

    GradeEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }}
