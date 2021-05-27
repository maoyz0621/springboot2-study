/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.quartz.jobdetail;

import com.myz.timer.quartz.job.FirstJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author maoyz on 2018/8/5
 * @version: v1.0
 */
public class FirstJobDetail {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 2 建立job,与Job绑定
     */
    public JobDetail jobDetail(String name, String group) {
        logger.debug("FirstJobDetail jobDetail()");
        // 唯一标识
        JobKey firstKey = new JobKey(name, group);

        // 设置JobDataMap,注意setJobData()和usingJobData()不同一起使用，否则和usingJobData()失效
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", "first_message");
        jobDataMap.put("second", 2);

        JobDetail jobDetail = JobBuilder.newJob(FirstJob.class)
                .withDescription("这是第一个任务")
                .setJobData(jobDataMap)
                .withIdentity(firstKey)
                .build();

        return jobDetail;
    }
}
