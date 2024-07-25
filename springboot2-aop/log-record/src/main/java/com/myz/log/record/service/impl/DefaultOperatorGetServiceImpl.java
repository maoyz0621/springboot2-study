/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.service.impl;

import com.myz.log.record.entity.Operator;
import com.myz.log.record.service.IOperatorGetService;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class DefaultOperatorGetServiceImpl implements IOperatorGetService {

    private Operator operator;

    public DefaultOperatorGetServiceImpl() {
        operator = new Operator("", "");
    }

    @Override
    public Operator getUser() {
        return operator;
    }

}