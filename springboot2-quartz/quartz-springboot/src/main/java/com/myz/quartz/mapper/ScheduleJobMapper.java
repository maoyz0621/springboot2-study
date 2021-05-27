package com.myz.quartz.mapper;

import com.myz.quartz.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleJobMapper {

    int deleteByPrimaryKey(Long scheduleJobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Long scheduleJobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

    List<ScheduleJob> getAll();
}