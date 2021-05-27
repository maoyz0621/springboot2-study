package com.myz.quartz.service.impl;

import com.myz.quartz.entity.ScheduleJob;
import com.myz.quartz.mapper.ScheduleJobMapper;
import com.myz.quartz.service.ScheduleJobService;
import com.myz.quartz.support.ScheduleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 *
 * @author maoyz0621
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);

    /**
     * 定时任务操作类
     */
    @Autowired
    private ScheduleManager scheduleManager;

    @Resource
    private ScheduleJobMapper scheduleJobMapper;


    @Override
    public void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            scheduleManager.addJob(jobClassName, jobGroupName, cronExpression, "");
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobName(jobClassName);
            scheduleJob.setJobGroup(jobGroupName);
            scheduleJob.setCronExpression(cronExpression);
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setDescription("");
            scheduleJob.setJobStatus("1");
            // TODO 不能添加相同任务,需要做判断
            int insertSelective = scheduleJobMapper.insertSelective(scheduleJob);
            if (insertSelective < 1) {
                // todo
                logger.warn("================ 定时任务入库 Failure ================");
            }
            logger.info("================ 定时任务入库Success ================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
        try {
            scheduleManager.jobPause(jobClassName, jobGroupName);
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobName(jobClassName);
            scheduleJob.setJobGroup(jobGroupName);
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setDescription("");
            scheduleJob.setJobStatus("0");
            int update = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
            if (update < 1) {
                // todo
                logger.warn("================ 定时任务更新 Failure ================");
            }
            logger.info("================ 定时任务更新Success ================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jobResume(String jobClassName, String jobGroupName) throws Exception {
        try {
            scheduleManager.jobResume(jobClassName, jobGroupName);
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobName(jobClassName);
            scheduleJob.setJobGroup(jobGroupName);
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setDescription("");
            scheduleJob.setJobStatus("1");
            int update = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
            if (update < 1) {
                // todo
                logger.warn("================ 定时任务更新 Failure ================");
            }
            logger.info("================ 定时任务更新Success ================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jobReschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            scheduleManager.updateScheduleJob(jobClassName, jobGroupName, cronExpression);
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobName(jobClassName);
            scheduleJob.setJobGroup(jobGroupName);
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setDescription("");
            scheduleJob.setJobStatus("1");
            scheduleJob.setCronExpression(cronExpression);
            int update = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
            if (update < 1) {
                // todo
                logger.warn("================ 定时任务更新 Failure ================");
            }
            logger.info("================ 定时任务更新Success ================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void jobDelete(String jobClassName, String jobGroupName) throws Exception {
        scheduleManager.jobDelete(jobClassName, jobGroupName);

        try {
            scheduleManager.jobDelete(jobClassName, jobGroupName);
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobName(jobClassName);
            scheduleJob.setJobGroup(jobGroupName);
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setDescription("");
            scheduleJob.setJobStatus("1");
            int delete = scheduleJobMapper.deleteByPrimaryKey(scheduleJob.getScheduleJobId());
            if (delete < 1) {
                // todo
                logger.warn("================ 定时任务更新 Failure ================");
            }
            logger.info("================ 定时任务更新Success ================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ScheduleJob> getAllJobs(Integer pageNum, Integer pageSize) {
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.getAll();
        if (scheduleJobs != null) {
            return scheduleJobs;
        }
        return new ArrayList<ScheduleJob>();
    }

    /**
     * 默认查询50条
     */
    @Override
    public List<ScheduleJob> getAllJobs() {
        return getAllJobs(1, 50);
    }
}
