/**
 * Copyright 2020 Inc.
 **/
package com.myz.job.bean.frameless.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 20-4-25
 * @version v1.0
 */
@Slf4j
public class FramelessJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("XXL-JOB, FramelessJobHandler");
        for (int i = 0; i < 5; i++) {
            log.info("============ FramelessJobHandler ================");
            XxlJobLogger.log(" start :" + i);
            // 模拟业务用时
            TimeUnit.SECONDS.sleep(3);
        }
        return SUCCESS;
    }
}
