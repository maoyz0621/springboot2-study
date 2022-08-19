/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.es.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author maoyz0621 on 2022/3/21
 * @version v1.0
 */
@Data
@Accessors(chain = true)
public class UserQueryCondition extends QueryCondition {

    private String username;

    private Integer age;

    private String address;

    private Boolean men;

    private Double height;

    private String telephone;

    private Date birth;

    private Date birth1;

    private List<String> goods;

    private Long userId;
}