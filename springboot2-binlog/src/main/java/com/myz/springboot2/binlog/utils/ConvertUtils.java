/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.myz.springboot2.binlog.domain.SchemaColumnsDo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/9/28
 * @version v1.0
 */
public class ConvertUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    // 将对象转成字符串
    public static String objToString(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    // 将Map转成指定的Bean
    public static <T> T mapConvertToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
        Map<String, Object> newParam = Maps.newHashMapWithExpectedSize(map.size());
        // 下划线转驼峰
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            newParam.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey()), entry.getValue());
        }

        return mapper.readValue(objToString(newParam), clazz);
    }

    // 将Bean转成Map
    public static Map beanConvertToMap(Object obj) throws Exception {
        return mapper.readValue(objToString(obj), Map.class);
    }

    public static void main(String[] args) throws Exception {
        Map param = new HashMap();
        param.put("tableSchema", "1");
        SchemaColumnsDo schemaColumnsDoClass = mapConvertToBean(param, SchemaColumnsDo.class);
        System.out.println(schemaColumnsDoClass);
    }
}