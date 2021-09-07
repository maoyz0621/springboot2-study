/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myz.springboot2.jackson.config.serial.MyNumberJackJsonSerializer;
import com.myz.springboot2.jackson.config.serial.MyStatusJackJsonSerializer;
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
    @JsonSerialize(using = MyNumberJackJsonSerializer.class)
    private BigDecimal feePrice;

    /**
     * 保留2位小数
     */
    @JsonSerialize(using = MyNumberJackJsonSerializer.class)
    private BigDecimal preferentialPrice;

    @JsonSerialize(using = MyStatusJackJsonSerializer.class)
    private int status;
}