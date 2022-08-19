/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.es.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author maoyz0621 on 2021/7/29
 * @version v1.0
 */
@Data
@Accessors(chain = true)
public class QueryCondition {

    private String index;
    private int pageNo = 0;
    private int pageSize = 10;
    private String name;
}