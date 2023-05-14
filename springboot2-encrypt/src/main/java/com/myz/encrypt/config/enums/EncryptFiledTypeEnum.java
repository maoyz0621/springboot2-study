/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author maoyz0621 on 2023/5/15
 * @version v1.0
 */
@Getter
@AllArgsConstructor
public enum EncryptFiledTypeEnum {

    TO_STRING(1, "小学"),
    TO_LONG(2, "中学"),
    TO_OBJ(3, "高中"),
    ;


    private final int code;
    private final String desc;
}