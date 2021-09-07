/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.myz.springboot2.jackson.config.format.DateToLongFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 接收Form表单数据
 *
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Data
@ToString
public class UserFormBean {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    @DateToLongFormat(pattern = "yyyy-MM-dd")
    private Long dateToLong;

    @DateToLongFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long dateTimeToLong;

    @JsonDeserialize()
    private int status;

}