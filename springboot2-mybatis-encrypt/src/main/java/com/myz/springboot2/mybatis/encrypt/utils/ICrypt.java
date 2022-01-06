/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.utils;

import java.lang.reflect.Field;

/**
 * @author maoyz0621 on 2020/11/2
 * @version v1.0
 */
public interface ICrypt {

    /**
     * 加密方法
     *
     * @param declaredFields  反射bean成员变量
     * @param parameterObject Mybatis入参
     * @return
     */
    <T> T encrypt(Field[] declaredFields, T parameterObject) throws IllegalAccessException;


    /**
     * 解密方法
     *
     * @param result Mybatis 返回值，需要判断是否是ArrayList类型
     * @return
     */
    <T> T decrypt(T result) throws IllegalAccessException;
}