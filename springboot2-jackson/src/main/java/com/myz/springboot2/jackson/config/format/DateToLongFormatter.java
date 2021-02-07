/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.config.format;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 字符串日期类型转Long 实现类
 *
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
public class DateToLongFormatter implements Formatter<Long> {

    private String pattern;

    public DateToLongFormatter() {
    }

    public DateToLongFormatter(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 将字符串日期类型转Long
     * TODO 国际化
     *
     * @param text
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public Long parse(String text, Locale locale) throws ParseException {
        if (!StringUtils.hasLength(text)) {
            return null;
        }

        if (StringUtils.hasLength(pattern)) {
            try {
                // TODO 线程安全实现类
                SimpleDateFormat dateFormat = new SimpleDateFormat(this.pattern);
                return dateFormat.parse(text).getTime();
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 将Long转字符串日期类型
     *
     * @param object
     * @param locale
     * @return
     */
    @Override
    public String print(Long object, Locale locale) {
        return null;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}