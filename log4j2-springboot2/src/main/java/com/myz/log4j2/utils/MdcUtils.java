/**
 * Copyright 2024 Inc.
 **/
package com.myz.log4j2.utils;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;

/**
 * @author maoyz0621 on 2024/7/8
 * @version v1.0
 */
public class MdcUtils {
    public static final String DEFAULT_MDC_TRACE_ID = "0";

    public static final String MDC_TRACE_ID_KEY = "traceId";

    public static final String HTTP_HEADER_REQUEST_ID = "X-Request-ID";

    public static String getOrDefault(String key, String defaultValue) {
        String value = MDC.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static void put(String key, String value) {
        MDC.put(key, value);
    }

    public static String getTraceIdOrDefault() {
        String traceId = MDC.get(MDC_TRACE_ID_KEY);
        if (traceId == null) {
            return DEFAULT_MDC_TRACE_ID;
        }
        return traceId;
    }

    public static String getTraceId() {
        String traceId = MDC.get(MDC_TRACE_ID_KEY);
        if (traceId == null) {
            traceId = IdUtil.fastUUID();
            MDC.put(MDC_TRACE_ID_KEY, traceId);
        }
        return traceId;
    }

    public static String setTraceId(String traceId) {
        if (traceId == null) {
            traceId = IdUtil.fastUUID();
        }
        MDC.put(MDC_TRACE_ID_KEY, traceId);
        return traceId;
    }

    public static void clear() {
        MDC.clear();
    }

}