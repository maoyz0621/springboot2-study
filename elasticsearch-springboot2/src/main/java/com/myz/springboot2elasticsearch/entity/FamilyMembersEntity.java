/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author maoyz0621 on 2021/7/29
 * @version v1.0
 */
@Data
public class FamilyMembersEntity implements Serializable {
    private static final long serialVersionUID = 2056218909335960186L;

    private String father;
    private String mother;
    private String wife;
}