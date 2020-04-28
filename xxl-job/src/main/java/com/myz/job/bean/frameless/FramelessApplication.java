/**
 * Copyright 2020 Inc.
 **/
package com.myz.job.bean.frameless;

import com.myz.job.bean.frameless.config.XxlJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 无框架依赖　启动
 *
 * @author maoyz0621 on 20-4-25
 * @version v1.0
 */

public class FramelessApplication {
    private static final Logger logger = LoggerFactory.getLogger(FramelessApplication.class);

    public static void main(String[] args) {
        try {
            // 启动
            XxlJobConfig.getInstance().initJobExecutor();
            while (true) {
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭
            XxlJobConfig.getInstance().destoryXxlJobExecutor();
        }
    }
}
