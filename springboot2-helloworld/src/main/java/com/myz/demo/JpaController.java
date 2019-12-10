package com.myz.demo;

import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author maoyz on 18-1-4.
 */
@RestController
public class JpaController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    /**
     * 查询一个
     */
    @GetMapping("/jpa/{id}")
    public String select(@PathVariable Integer id) {
        UserEntity user = userRepository.findOne(id);
        return user.toString();
    }

    /**
     * 查询所有
     */
    @RequestMapping("/all")
    public List<UserEntity> selectAll() {
        List<UserEntity> all = userRepository.findAll();
        return all;
    }

    /**
     * 插入数据
     */
    @PostMapping("/insert")
    public UserEntity insert() {
        UserEntity user = new UserEntity();
        user.setUsername("aaa");
        user.setAge(19);
        return userRepository.save(user);
    }

    /**
     * 更新一个
     *
     * @param id
     * @return
     */
    @PutMapping("/jpa/{id}")
    public String update(@PathVariable Integer id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername("bbbb");
        user.setAge(12);
        userRepository.save(user);
        return user.toString();
    }

    /**
     * 删除一个
     *
     * @param id
     * @return
     */
    @DeleteMapping("/jpa/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.delete(id);
    }

    /**
     * 通过年龄查询
     */
    @GetMapping("/jpa/age/{age}")
    public List<UserEntity> findByAge(@PathVariable Integer age) {
        List<UserEntity> lists = userRepository.findByAge(age);
        return lists;
    }

    /**
     * 测试事务
     */
    @PostMapping("/jpa/testTransactional")
    public void testTransactional() {
        userService.insert();
    }
}
