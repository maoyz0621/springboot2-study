/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.annotaion;

/**
 * @author maoyz on 18-11-5
 * @version: v1.0
 */
public @interface TargetDataSource {

    String value() default "";
}
