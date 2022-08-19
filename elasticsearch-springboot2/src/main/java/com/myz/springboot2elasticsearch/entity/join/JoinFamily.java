/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.entity.join;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.JoinTypeRelation;
import org.springframework.data.elasticsearch.annotations.JoinTypeRelations;
import org.springframework.data.elasticsearch.core.join.JoinField;

import java.util.List;

/**
 * @author maoyz0621 on 2022/4/21
 * @version v1.0
 */
@Document(indexName = "join_family", createIndex = true, shards = 2, replicas = 1, refreshInterval = "-1")
@Data
public class JoinFamily {
    @Id
    @Field(type = FieldType.Long, store = true)
    private Long myId;

    @Field(type = FieldType.Keyword, store = true)
    private String name;

    @Field(type = FieldType.Keyword, store = true)
    private String level;

    @Field(type = FieldType.Object, store = true)
    private JoinField joinFiled;

    @JoinTypeRelations(relations = {
            @JoinTypeRelation(parent = "grand_parent", children = "parent"),
            @JoinTypeRelation(parent = "parent", children = "child")
    })
    private List<JoinFamily> child;
}