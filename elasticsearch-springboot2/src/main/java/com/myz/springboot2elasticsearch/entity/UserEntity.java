/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * elasticSearch启用 @Document(indexName = "maoyz", type = "user", createIndex = true)
 *
 * @author maoyz0621 on 19-1-30
 * @version v1.0
 */
@Document(indexName = "user", createIndex = true)
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 3217448931285622026L;

    @Id
    private Long id;

    @Field
    private String username;

    private Integer age;
}
