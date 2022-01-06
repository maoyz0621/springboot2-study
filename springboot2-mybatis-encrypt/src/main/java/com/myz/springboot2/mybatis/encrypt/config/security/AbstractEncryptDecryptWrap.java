/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.security;

import java.lang.reflect.Field;

/**
 * @author maoyz0621 on 2021/8/14
 * @version v1.0
 */
public abstract class AbstractEncryptDecryptWrap {

    abstract <T> T encrypt(Field[] declaredFields, T parameterObject) throws IllegalAccessException;

    abstract <T> T encrypt(Field declaredField, T parameterObject) throws IllegalAccessException;

    abstract <T> T decrypt(T result) throws IllegalAccessException;
}