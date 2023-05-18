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

    TO_STRING(1, "to_string"),
    TO_LONG(2, "to_long"),
    TO_OBJ(3, "to_obj"),
    ;


    private final int code;
    private final String desc;
}