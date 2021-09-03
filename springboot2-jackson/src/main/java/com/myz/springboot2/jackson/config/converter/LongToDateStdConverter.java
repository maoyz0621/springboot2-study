/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Date;

/**
 * 前端Long传到后端 转日期
 *
 * @author maoyz0621 on 2021/9/2
 * @version v1.0
 */
public class LongToDateStdConverter extends StdConverter<Long, Date> {

    @Override
    public Date convert(Long value) {
        return new Date(value);
    }
}