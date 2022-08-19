/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.entity.join;

import lombok.Data;

/**
 * @author maoyz0621 on 2022/4/21
 * @version v1.0
 */
@Data
public class ChildCount {
    private String parentName;
    private long childCount;
    private String children;
}