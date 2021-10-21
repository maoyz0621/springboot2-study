/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.es.model;

import lombok.Data;

/**
 * @author maoyz0621 on 2021/7/29
 * @version v1.0
 */
@Data
public class QueryCondition {

    private String index;
    private int pageNo;
    private int pageSize;
    private String name;
}