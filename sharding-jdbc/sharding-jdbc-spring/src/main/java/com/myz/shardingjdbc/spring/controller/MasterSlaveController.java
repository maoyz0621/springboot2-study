/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-28 10:14  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.spring.controller;

import com.myz.shardingjdbc.spring.manager.MasterSlave1TonManager;
import com.myz.shardingjdbc.spring.manager.MasterSlavenTonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz
 */
@RestController
public class MasterSlaveController {

    @Autowired
    MasterSlave1TonManager masterSlave1TonManager;

    @Autowired
    MasterSlavenTonManager masterSlavenTonManager;

    public String index(){
        return null;
    }
}