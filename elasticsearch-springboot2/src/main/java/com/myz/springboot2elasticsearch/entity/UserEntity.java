/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * elasticSearch启用 @Document(indexName = "maoyz", type = "user", createIndex = true)
 *
 * @author maoyz0621 on 19-1-30
 * @version v1.0
 */
@Document(indexName = "user", createIndex = true, shards = 2, replicas = 2)
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 3217448931285622026L;

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "")
    private String username;

    private Integer age;

    @Field(type = FieldType.Keyword)
    private String address;

    @Field(type = FieldType.Boolean)
    private boolean men;

    @Field(type = FieldType.Double)
    private double height;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date birth;

    @Field(type = FieldType.Auto)
    private List<String> goods;


    @Field(type = FieldType.Object)
    private FamilyMembersEntity familyMembers;
}
