/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 前端"2021-08-02 11:11:12" 传到后端转Long
 *
 * @author maoyz0621 on 2021/9/2
 * @version v1.0
 */
public class DateToLongStdConverter extends StdConverter<String, Long> {

    @Override
    public Long convert(String val) {
        if (StringUtils.isEmpty(val)) {
            return null;
        }
        LocalDateTime parse = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            parse = LocalDateTime.parse(val, formatter);
        } catch (Exception e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            parse = LocalDateTime.parse(val, formatter);
        }
        return parse.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}