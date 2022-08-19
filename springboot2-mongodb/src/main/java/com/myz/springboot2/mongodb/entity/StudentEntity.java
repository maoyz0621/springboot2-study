/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.mongodb.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author maoyz0621 on 2022/6/16
 * @version v1.0
 */
@Document(collection = "mydb")
@Data
@Accessors(chain = true)
public class StudentEntity {

    @Id
    private Long id;

    private String username;

    private Date birth;

    private Integer height;
}