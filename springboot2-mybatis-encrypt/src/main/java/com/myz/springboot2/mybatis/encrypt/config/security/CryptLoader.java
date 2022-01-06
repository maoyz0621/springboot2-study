/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.security;

import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptClass;
import com.myz.springboot2.mybatis.encrypt.utils.CryptImpl;

/**
 * @author maoyz0621 on 2021/10/31
 * @version v1.0
 */
public class CryptLoader {

    public void loadCrypt() {
        CryptContext.setCrypt(CryptClass.CryptTypeEnum.CARD, new CryptImpl());
    }
}