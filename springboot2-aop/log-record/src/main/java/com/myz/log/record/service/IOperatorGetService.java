/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.service;

import com.myz.log.record.entity.Operator;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public interface IOperatorGetService {

    /**
     * 可以在里面外部的获取当前登陆的用户，比如 UserContext.getCurrentUser()
     *
     * @return 转换成Operator返回
     */
    Operator getUser();
}