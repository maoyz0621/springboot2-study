package com.myz.springboot2.mybatisplus34.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myz.springboot2.mybatisplus34.entity.EmpEntity;
import com.myz.springboot2.mybatisplus34.mapper.EmpMapper;
import com.myz.springboot2.mybatisplus34.service.IEmpService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maoyz
 * @since 2021-12-14
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, EmpEntity> implements IEmpService {

}
