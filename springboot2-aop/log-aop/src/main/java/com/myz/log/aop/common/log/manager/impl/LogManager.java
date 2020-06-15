/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.manager.impl;


import com.myz.log.aop.common.log.manager.ILogManager;
import com.myz.log.aop.common.log.manager.model.LogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
@Service
public class LogManager implements ILogManager {

    private final Logger logger = LoggerFactory.getLogger(LogManager.class);

    @Override
    public void handleLog(LogModel logModel) {
        logger.debug("handle log ...");
    }
}
