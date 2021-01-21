/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.common.utils;

/**
 * @author maoyz0621 on 2020/10/15
 * @version v1.0
 */
public class DateTimeUtils {

    // 以T分隔日期和时间，并带时区信息，符合ISO8601规范
    public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String PATTERN_ISO_ON_SECOND = "yyyy-MM-dd'T'HH:mm:ssZZ";
    public static final String PATTERN_ISO_ON_DATE = "yyyy-MM-dd";

    // 以空格分隔日期和时间，不带时区信息
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DEFAULT_ON_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DEFAULT_ON_DATE = "yyyy-MM-dd";

}