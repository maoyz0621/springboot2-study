/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.module.service.impl;


import com.myz.log.aop.common.log.annotation.Log;
import com.myz.log.aop.common.log.annotation.LogEvent;
import com.myz.log.aop.module.service.IIndexService;
import org.springframework.stereotype.Service;

/**
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
@Service
@LogEvent
public class IndexServiceImpl implements IIndexService {

    @Log
    @LogEvent
    @Override
    public String index(String val) {
        return val;
    }
}
