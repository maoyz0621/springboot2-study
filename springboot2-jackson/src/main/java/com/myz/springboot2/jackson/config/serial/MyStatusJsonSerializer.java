/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.jackson.config.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 自定义Json序列化
 * 根据状态码 返回对应的显示值
 *
 * @author maoyz0621 on 2020/10/26
 * @version v1.0
 */
public class MyStatusJsonSerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer status, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String statusDesc = "";
        switch (status) {
            case 0:
                statusDesc = "暂存";
                break;
            case 1:
                statusDesc = "待上报";
                break;
            case 2:
                statusDesc = "待审核";
                break;
            case 3:
                statusDesc = "已审";
            default:
                statusDesc = "状态信息不符合";
        }
        jsonGenerator.writeString(statusDesc);
    }
}