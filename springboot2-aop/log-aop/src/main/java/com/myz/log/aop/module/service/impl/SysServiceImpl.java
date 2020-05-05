/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.module.service.impl;


import com.myz.log.aop.common.log.annotation.*;
import com.myz.log.aop.module.model.SysModel;
import com.myz.log.aop.module.service.ISysService;
import org.springframework.stereotype.Service;

/**
 * @author maoyz0621 on 2018/12/29
 * @version: v1.0
 */
@Service
@Log()
@LogEvent(module = LogModuleType.SYS)
public class SysServiceImpl implements ISysService {

    @Log
    @LogEvent(event = LogEventType.DELETE_SINGLE, desc = "删除记录")
    @Override
    public void deleteById(@LogKey(keyName = "id") String id, String a) {

    }

    @LogEvent(event = LogEventType.ADD, desc = "保存记录")
    @Override
    public int save(SysModel sysModel) {
        return 0;
    }

    @LogEvent(event = LogEventType.UPDATE, desc = "更新记录")
    @Override
    public void update(SysModel sysModel) {

    }

    @Override
    public void queryById(String id) {

    }
}
