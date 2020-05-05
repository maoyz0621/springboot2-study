/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.module.service;


import com.myz.log.aop.module.model.SysModel;

/**
 * @author maoyz0621 on 2018/12/29
 * @version: v1.0
 */
public interface ISysService {

    void deleteById(String id, String a);

    int save(SysModel sysModel);

    void update(SysModel sysModel);

    void queryById(String id);
}

