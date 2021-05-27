package com.myz.quartz.service;


import com.myz.quartz.entity.ScheduleJob;

import java.util.List;

/**
 * 定时任务service
 *
 * @author maoyz0621
 */
public interface ScheduleJobService {

    void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception;

    void jobPause(String jobClassName, String jobGroupName) throws Exception;

    void jobResume(String jobClassName, String jobGroupName) throws Exception;

    void jobReschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception;

    void jobDelete(String jobClassName, String jobGroupName) throws Exception;

    List<ScheduleJob> getAllJobs(Integer pageNum, Integer pageSize);

    List<ScheduleJob> getAllJobs();

}
