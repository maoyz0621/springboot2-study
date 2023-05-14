/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.entity;

import com.myz.encrypt.config.annotation.EncryptFiled;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author maoyz0621 on 2023/5/4
 * @version v1.0
 */
@Data
@Accessors(chain = true)
public class RespParam {

    @EncryptFiled
    private String username;

    private Integer age;

    @EncryptFiled
    private List<RespParam> child;
}