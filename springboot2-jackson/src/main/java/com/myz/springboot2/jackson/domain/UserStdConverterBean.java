/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myz.springboot2.jackson.config.converter.DateToLongStdConverter;
import com.myz.springboot2.jackson.config.converter.LongToDateStdConverter;
import com.myz.springboot2.jackson.config.converter.LongToDateStrStdConverter;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author maoyz0621 on 2021/9/2
 * @version v1.0
 */
@Data
public class UserStdConverterBean {

    @JsonDeserialize(converter = LongToDateStdConverter.class)
    private Date date;

    @JsonDeserialize(converter = LongToDateStdConverter.class)
    private Date dateTime;

    @JsonDeserialize(converter = DateToLongStdConverter.class)
    @JsonSerialize(converter = LongToDateStdConverter.class)
    private Long dateToLong;

    @JsonDeserialize(converter = DateToLongStdConverter.class)
    @JsonSerialize(converter = LongToDateStrStdConverter.class)
    private Long dateTimeToLong;

    public static void main(String[] args) throws Exception {
        UserStdConverterBean bean = new UserStdConverterBean();
        bean.setDate(new Date());
        bean.setDateTime(new Date());
        bean.setDateToLong(System.currentTimeMillis());
        bean.setDateTimeToLong(System.currentTimeMillis());
        ObjectMapper objectMapper = new ObjectMapper();
        ;
        System.out.println(objectMapper.writeValueAsString(bean));
    }
}