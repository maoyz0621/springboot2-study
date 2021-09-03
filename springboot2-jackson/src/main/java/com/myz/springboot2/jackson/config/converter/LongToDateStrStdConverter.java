/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Long时间戳 到 前段转日期Str
 *
 * @author maoyz0621 on 2021/9/2
 * @version v1.0
 */
public class LongToDateStrStdConverter extends StdConverter<Long, String> {

    public static final String PATTERN_DEFAULT_ON_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static ThreadLocal<DateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(PATTERN_DEFAULT_ON_SECOND));

    @Override
    public String convert(Long value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("+8"));
        // return LocalDate.now().format(formatter);
        return threadLocal.get().format(new Date(value));
    }
}