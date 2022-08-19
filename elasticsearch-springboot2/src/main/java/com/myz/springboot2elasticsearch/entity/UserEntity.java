/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myz.springboot2elasticsearch.es.constants.BizEsConstant;
import lombok.Data;
import lombok.experimental.Accessors;
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
@Document(indexName = BizEsConstant.INDEX_USER, createIndex = true, shards = 2, replicas = 2, refreshInterval = "-1")
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 3217448931285622026L;

    /**
     * 使用@Id
     * 表示该字段同时会被映射到Elasticsearch中document的_id字段（也就是说在Elasticsearch中_id的值与id值一样）
     */
    @Id
    @Field(type = FieldType.Long, store = true)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "", store = true)
    private String username;

    @Field(type = FieldType.Integer, store = true)
    private Integer age;

    @Field(type = FieldType.Text, store = true)
    private String address;

    @Field(type = FieldType.Boolean, store = true)
    private boolean men;

    @Field(type = FieldType.Double, store = true)
    private double height;

    @Field(type = FieldType.Keyword, store = true)
    private String telephone;

    /**
     * 保存时间类型
     * Property UserEntity.birth is annotated with FieldType.Date but has no DateFormat defined
     * Property UserEntity.birth is annotated with FieldType.Date and a custom format but has no pattern defined
     * <p>
     * 时间范围查找需求：存储Date，和取出来也是Date
     * 存储的时候利用各种JSON对象，如 Jackson 等。存储的时候就可以用JSON Format一下再存储，然后取出来后
     * 1646323755594
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "epoch_millis", store = true)
    private Date birth;

    /**
     * "birth1" : "2022-03-01 23:26:35"
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis", store = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birth1;

    @Field(type = FieldType.Auto, store = true)
    private List<String> goods;

    @Field(type = FieldType.Long, store = true)
    private Long userId;

    @Field(type = FieldType.Object, store = true)
    private FamilyMembersEntity familyMembers;

    @Override
    public Object clone() {
        UserEntity clone = null;
        try {
            clone = (UserEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
