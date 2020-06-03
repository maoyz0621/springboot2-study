/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.module.model;


import com.myz.log.aop.common.log.annotation.LogKey;
import lombok.Getter;
import lombok.Setter;

/**
 * @author maoyz0621 on 2018/12/29
 * @version: v1.0
 */
@Setter
@Getter
public class SysModel {

    @LogKey(isUserId = true)
    private String sysId;
    private String sysName;
    private Integer sysVersion;
}