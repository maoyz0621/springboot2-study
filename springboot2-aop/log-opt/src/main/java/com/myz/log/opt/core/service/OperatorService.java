/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.service;

import com.myz.log.opt.core.model.Operator;

/**
 * 操作者信息
 *
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public interface OperatorService {

    /**
     * 获取操作者信息
     *
     * @return Operator
     */
    Operator getOperator();
}