/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.mybatisplus.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.ToString;

/**
 * 方法二：实现IEnum接口，推荐配置defaultEnumTypeHandler
 *
 * @author maoyz0621 on 19-4-9
 * @version: v1.0
 */
// @ToString
public enum AgeEnum implements IEnum<Integer> {
    ONE(1, "一岁"),
    TWO(2, "二岁"),
    THREE(3, "三岁");

    private final int value;
    private final String desc;

    AgeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return null;
    }}
