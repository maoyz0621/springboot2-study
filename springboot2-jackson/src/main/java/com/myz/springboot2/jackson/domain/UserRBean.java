/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myz.springboot2.jackson.config.serial.MyNumberJsonSerializer;
import com.myz.springboot2.jackson.config.serial.MyStatusJsonSerializer;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Data
@ToString
public class UserRBean {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    @JsonSerialize
    private Date longToDate;

    @JsonSerialize
    private Date longToDateTime;

    /**
     * 转义 **
     */
    @JsonSerialize(using = MyNumberJsonSerializer.class)
    private BigDecimal feePrice;

    /**
     * 保留2位小数
     */
    @JsonSerialize(using = MyNumberJsonSerializer.class)
    private BigDecimal preferentialPrice;

    @JsonSerialize(using = MyStatusJsonSerializer.class)
    private int status;
}