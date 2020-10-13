package com.myz.springboot2.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myz.springboot2.mybatisplus.Springboot2MybatisPlusApplicationTests;
import com.myz.springboot2.mybatisplus.pojo.EmpPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 测试乐观锁的使用
 *
 * @author maoyz0621 on 19-4-13
 * @version: v1.0
 */
public class EmpMapperTest extends Springboot2MybatisPlusApplicationTests {

    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testUpdateByIdSuccess() {
        System.out.println("*******************************");
        EmpPO emp = new EmpPO();
        emp.setName("a");
        emp.setVersion(1);
        empMapper.insert(emp);
        Long id = emp.getId();
        EmpPO empPO = empMapper.selectById(id);
        // EmpPO(name=a, email=null, version=1)
        System.out.println(empPO);
    }

    @Test
    public void testUpdateByIdFailure() {
        System.out.println("*******************************");
        EmpPO emp = new EmpPO();
        emp.setName("a");
        emp.setVersion(1);
        empMapper.insert(emp);
        Long id = emp.getId();
        EmpPO empPO = empMapper.selectById(id);
        // EmpPO(name=a, email=null, version=1)
        System.out.println(empPO);

        System.out.println("=========================");

        EmpPO userUpdate = new EmpPO();
        userUpdate.setId(id);
        userUpdate.setName("b");
        userUpdate.setVersion(2);
        empMapper.updateById(userUpdate);
        EmpPO empPO1 = empMapper.selectById(id);
        // EmpPO(name=a, email=null, version=1) 更新失败
        System.out.println(empPO1);
    }

    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        System.out.println("*******************************");
        EmpPO emp = new EmpPO();
        emp.setName("a");
        emp.setVersion(1);
        empMapper.insert(emp);
        Long id = emp.getId();
        EmpPO empPO = empMapper.selectById(id);
        // EmpPO(name=a, email=null, version=1)
        System.out.println(empPO);

        System.out.println("=========================");

        EmpPO userUpdate = new EmpPO();
        userUpdate.setId(id);
        userUpdate.setName("b");
        // 设置version = null,更新成功
        userUpdate.setVersion(null);
        empMapper.updateById(userUpdate);
        EmpPO empPO1 = empMapper.selectById(id);
        // EmpPO(name=b, email=null, version=1),更新成功
        System.out.println(empPO1);
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * update(et,ew) et:必须带上version的值才会触发乐观锁
     */
    @Test
    public void testUpdateByEntitySuccess() {
        QueryWrapper<EmpPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version", 1);
        int count = empMapper.selectCount(queryWrapper);
        System.out.println("count = " + count);

        EmpPO emp = new EmpPO();
        emp.setName("a");
        emp.setVersion(1);
        // 执行更新
        empMapper.update(emp, queryWrapper);

        List<EmpPO> users = empMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testPage() {
        IPage<EmpPO> page = new Page<>();
        page.setSize(3);
        page.setTotal(2);
        IPage<EmpPO> page1 = empMapper.selectPage(page, null);
        long current = page1.getCurrent();
        System.out.println(current);
        List<EmpPO> records = page.getRecords();
        System.out.println(records);
    }

}