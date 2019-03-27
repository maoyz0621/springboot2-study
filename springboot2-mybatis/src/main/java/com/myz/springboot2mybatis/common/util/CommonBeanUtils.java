/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.util;

import org.springframework.beans.BeanUtils;

/**
 * BeanUtils工具类
 *
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
public class CommonBeanUtils {

    public static void copyProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target);
    }
}
