/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.utils;


import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptField;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 加解密工具类
 *
 * @author maoyz0621 on 2020/11/2
 * @version v1.0
 */
public class EncryptDecryptUtils {

    /**
     * 多field加密方法
     *
     * @param declaredFields
     * @param parameterObject
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> T encrypt(Field[] declaredFields, T parameterObject) throws IllegalAccessException {
        for (Field field : declaredFields) {
            CryptField annotation = field.getAnnotation(CryptField.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            encrypt(field, parameterObject);
        }
        return parameterObject;
    }


    /**
     * 单个field加密方法
     *
     * @param field
     * @param parameterObject
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> T encrypt(Field field, T parameterObject) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(parameterObject);
        if (object instanceof BigDecimal) {
            BigDecimal value = (BigDecimal) object;
            long longValue = value.movePointRight(4).subtract(BigDecimal.valueOf(Integer.MAX_VALUE >> 3)).longValue();
            field.set(parameterObject, BigDecimal.valueOf(longValue));
        } else if (object instanceof Integer) {
            //TODO 定制Integer类型的加密算法
        } else if (object instanceof Long) {
            //TODO 定制Long类型的加密算法
        } else if (object instanceof String) {
            String value = (String) object;
            field.set(parameterObject, value + "000");
            //TODO 定制String类型的加密算法

        }
        return parameterObject;
    }

    /**
     * 解密方法
     *
     * @param result
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> T decrypt(T result) throws IllegalAccessException {
        Class<?> parameterObjectClass = result.getClass();
        Field[] declaredFields = parameterObjectClass.getDeclaredFields();
        decrypt(declaredFields, result);
        return result;
    }

    /**
     * 多个field解密方法
     *
     * @param declaredFields
     * @param result
     * @throws IllegalAccessException
     */
    public static void decrypt(Field[] declaredFields, Object result) throws IllegalAccessException {
        for (Field field : declaredFields) {
            CryptField annotation = field.getAnnotation(CryptField.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            decrypt(field, result);
        }
    }

    /**
     * 单个field解密方法
     *
     * @param field
     * @param result
     * @throws IllegalAccessException
     */
    public static void decrypt(Field field, Object result) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(result);
        if (object instanceof BigDecimal) {
            BigDecimal value = (BigDecimal) object;
            double doubleValue = value.add(BigDecimal.valueOf(Integer.MAX_VALUE >> 3)).movePointLeft(4).doubleValue();
            field.set(result, BigDecimal.valueOf(doubleValue));
        } else if (object instanceof Integer) {
            //TODO 定制Integer类型的加密算法
        } else if (object instanceof Long) {
            //TODO 定制Long类型的加密算法
        } else if (object instanceof String) {
            //TODO 定制String类型的加密算法
            String value = (String) object;
            field.set(result, value.replace("000", ""));

        }
    }
}