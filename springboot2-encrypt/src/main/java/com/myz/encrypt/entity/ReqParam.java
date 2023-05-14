/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author maoyz0621 on 2023/5/4
 * @version v1.0
 */
@Data
@Accessors(chain = true)
public class ReqParam {

    private String username;

    private Integer age;
}