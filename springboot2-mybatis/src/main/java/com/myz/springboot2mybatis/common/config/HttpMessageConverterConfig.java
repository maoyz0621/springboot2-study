/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maoyz0621 on 19-1-12
 * @version: v1.0
 */
@Configuration
public class HttpMessageConverterConfig {

    /**
     * <bean id="fastJsonHttpMessageConverter" class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4">
     *         <property name="supportedMediaTypes">
     *             <list>
     *                 <value>text/html;charset=UTF-8</value>
     *                 <value>application/json;charset=UTF-8</value>
     *             </list>
     *         </property>
     *         <property name="fastJsonConfig">
     *             <bean class="com.alibaba.fastjson.support.config.FastJsonConfig">
     *                 <property name="features">
     *                     <list>
     *                         <value>AllowArbitraryCommas</value>
     *                         <value>AllowUnQuotedFieldNames</value>
     *                         <value>DisableCircularReferenceDetect</value>
     *                     </list>
     *                 </property>
     *                 <property name="dateFormat" value="yyyy-MM-dd"/>
     *             </bean>
     *         </property>
     *     </bean>
     */
    @Bean("fastJsonHttpMessageConverter")
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypeList.add(MediaType.TEXT_HTML);
        fastConverter.setSupportedMediaTypes(mediaTypeList);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 格式化时间
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));

        fastJsonConfig.setFeatures(Feature.AllowArbitraryCommas,
                Feature.AllowUnQuotedFieldNames,
                Feature.DisableCircularReferenceDetect);

        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

}
