package com.myz.demo;

import com.example.entity.EmpEntity;
import com.example.service.EmpMybatisService;
import com.example.utils.ResultUtils;
import com.example.dto.Result;
import com.example.dto.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author maoyz on 18-1-4.
 */
@RestController
public class MybatisController {

    private final static Logger logger = LoggerFactory.getLogger(MybatisController.class);

    @Resource
    private EmpMybatisService empMybatisService;

    @GetMapping("/mybatis")
    public EmpEntity select(@RequestParam("ename") String username) {
        EmpEntity emp = empMybatisService.select(username);
        return emp;
    }

    @GetMapping("/mybatisAll")
    public Result<List<EmpEntity>> selectAll() {
        List<EmpEntity> emps = empMybatisService.selectAll();
        return ResultUtils.success(emps);
    }

    /**
     * 插入验证
     */
    @PostMapping("/mybatis")
    public Result<EmpEntity> insert(@Valid EmpEntity empEntity, BindingResult result) {
        if (result.hasErrors()) {
            logger.info(result.getFieldError().getDefaultMessage());
            return ResultUtils.error(ResultEnum.WRONG.getCode(), result.getFieldError().getDefaultMessage());
        }
        empMybatisService.insert(empEntity);
        return ResultUtils.success(empEntity);
    }

    @GetMapping("/mybatis/{name}")
    public void findAge(@PathVariable("name") String username) throws Exception {
        empMybatisService.findAge(username);
    }
}
