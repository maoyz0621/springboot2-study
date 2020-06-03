/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.redis1.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 无参构造 @NoArgsConstructor
 * 自动生成全参数构造函数 @AllArgsConstructor
 *
 * @author maoyz on 2018/4/21
 * @Version: v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private String name;
    private int age;
}
