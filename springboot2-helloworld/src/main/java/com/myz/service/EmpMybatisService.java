package com.myz.service;

import com.example.dto.ResultEnum;
import com.example.entity.EmpEntity;
import com.example.exception.EmpException;
import com.example.mapper.EmpMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author maoyz on 18-1-4.
 */
@Service
public class EmpMybatisService {

    @Resource
    private EmpMapper empMapper;

    public EmpEntity select(String username) {
        EmpEntity empEntity = empMapper.selectByName(username);
        return empEntity;
    }

    public List<EmpEntity> selectAll() {
        List<EmpEntity> empEntities = empMapper.selectAll();
        return empEntities;
    }

    public void insert(EmpEntity empEntity) {
        empEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 18));
        empEntity.setAge(20);
        int count = empMapper.insertEmp(empEntity);
        if (count != 0) {
            //    todo
        }
        //    todo
    }

    public void update(Map param) {
        int count = empMapper.update(param);
        if (count != 0) {
            //    todo
        }
        //    todo
    }

    public void del(Map param) {
        int count = empMapper.del(param);
        if (count != 0) {
            //    todo
        }
        //    todo
    }

    public void findAge(String username) throws Exception {
        EmpEntity empEntity = empMapper.selectByName(username);
        Integer age = empEntity.getAge();

        if (age < 10) {
            throw new EmpException(ResultEnum.AGE_SMALL);
        } else if (age > 10 && age < 16) {
            throw new EmpException(ResultEnum.AGE_LARGE);
        } else {
            throw new EmpException(ResultEnum.WRONG);
        }
    }


}
