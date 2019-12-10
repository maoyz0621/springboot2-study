/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.swagger.common.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author maoyz0621 on 19-1-12
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
@ApiModel(value = "用户")
public class User {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;
}
