/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.manager;

import com.myz.log.aop.common.log.manager.model.LogModel;

/**
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
public interface ILogManager {

    void handleLog(LogModel logModel);
}
