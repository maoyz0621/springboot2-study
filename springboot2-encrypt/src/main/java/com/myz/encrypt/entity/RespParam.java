/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.entity;

import com.myz.encrypt.config.annotation.EncryptFiled;
import com.myz.encrypt.config.enums.EncryptFiledTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author maoyz0621 on 2023/5/4
 * @version v1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RespParam {

    @EncryptFiled
    private String username;

    private Integer age;

    @EncryptFiled(type = EncryptFiledTypeEnum.TO_LONG)
    private Long phone;

    @EncryptFiled
    private List<RespParam> child;

    @EncryptFiled
    private RespParam parent;

    public RespParam(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public RespParam(String username, Integer age, Long phone) {
        this(username, age);
        this.phone = phone;
    }
}