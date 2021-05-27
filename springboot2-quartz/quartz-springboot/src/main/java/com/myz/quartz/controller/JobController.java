package com.myz.quartz.controller;//

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.myz.quartz.entity.ScheduleJob;
import com.myz.quartz.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 前后端分离
 *
 * @author maoyz0621
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @PostMapping(value = "/addjob")
    public void addJob(@RequestParam(value = "jobClassName") String jobClassName,
                       @RequestParam(value = "jobGroupName") String jobGroupName,
                       @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        scheduleJobService.addJob(jobClassName, jobGroupName, cronExpression);
    }


    @PostMapping(value = "/pausejob")
    public void pauseJob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        scheduleJobService.jobPause(jobClassName, jobGroupName);
    }


    @PostMapping(value = "/resumejob")
    public void resumeJob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        scheduleJobService.jobResume(jobClassName, jobGroupName);
    }


    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        scheduleJobService.jobReschedule(jobClassName, jobGroupName, cronExpression);
    }


    @PostMapping(value = "/deletejob")
    public void deleteJob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        scheduleJobService.jobDelete(jobClassName, jobGroupName);
    }


    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryJob(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<ScheduleJob> allJobs = scheduleJobService.getAllJobs(pageNum, pageSize);
        Map<String, Object> map = Maps.newHashMap();
        map.put("JobAndTrigger", allJobs);
        map.put("number", allJobs.size());
        return map;
    }


}