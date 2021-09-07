/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.jackson.config.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 自定义Json序列化
 *
 * @author maoyz0621 on 2020/10/26
 * @version v1.0
 */
public class MyNumberJackJsonSerializer extends JsonSerializer<BigDecimal> {
    /**
     * 保留2位小数 #.00 表示两位小数 #.0000四位小数
     */
    public static final DecimalFormat FORMAT;

    static {
        FORMAT = new DecimalFormat("#0.00");
        FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // null -> 0.00
        if (bigDecimal == null) {
            jsonGenerator.writeNumber(new BigDecimal("0.00"));
        } else if (new BigDecimal("-0.1").equals(bigDecimal)) {
            // **
            jsonGenerator.writeString("**");
        } else {
            jsonGenerator.writeNumber(FORMAT.format(bigDecimal));
        }
    }
}