/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.security;

import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptClass;
import com.myz.springboot2.mybatis.encrypt.utils.ICrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/10/31
 * @version v1.0
 */
public class CryptContext {

    public static Map<CryptClass.CryptTypeEnum, ICrypt> map = new HashMap<>();

    public static ICrypt getCrypt(CryptClass.CryptTypeEnum cryptTypeEnum) {
        return map.get(cryptTypeEnum);
    }

    public static void setCrypt(CryptClass.CryptTypeEnum cryptTypeEnum, ICrypt crypt) {
        map.put(cryptTypeEnum, crypt);
    }
}