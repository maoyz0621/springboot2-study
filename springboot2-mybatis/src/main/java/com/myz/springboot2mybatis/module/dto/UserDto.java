/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.module.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = -8972880245748996572L;

    private Integer userId;

    private String username;

    private Short age;

    private Date birthday;
}
