/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author maoyz0621 on 2021/7/29
 * @version v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMembersEntity implements Serializable {
    private static final long serialVersionUID = 2056218909335960186L;
    @Field(type = FieldType.Text, store = true)
    private String father;
    @Field(type = FieldType.Text, store = true)
    private String mother;
    @Field(type = FieldType.Text, store = true)
    private String wife;
}