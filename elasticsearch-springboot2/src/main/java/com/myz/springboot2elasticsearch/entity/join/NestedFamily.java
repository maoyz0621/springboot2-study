/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.entity.join;

import lombok.Data;

import java.util.List;

/**
 * @author maoyz0621 on 2022/4/21
 * @version v1.0
 */
@Data
public class NestedFamily {
    private String myId;
    private String name;
    private String level;
    private List<NestedFamily> child;
}