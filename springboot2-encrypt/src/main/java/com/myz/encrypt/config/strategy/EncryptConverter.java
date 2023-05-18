/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.strategy;

import com.myz.encrypt.config.enums.EncryptFiledTypeEnum;

/**
 * @author maoyz0621 on 2023/5/11
 * @version v1.0
 */
public interface EncryptConverter {

    <T> T convert(T obj, EncryptFiledTypeEnum filedTypeEnum);
}