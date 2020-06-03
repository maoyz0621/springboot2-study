package com.myz.springboot2.repository;

import com.myz.springboot2.entity.UserEnity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

/**
 * @author maoyz on 2018/8/27
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Before
  public void before() {
    userRepository.save(new UserEnity(2, "AAA", 10, new Date(System.currentTimeMillis())));
  }

  @Test
  public void findByAge() throws Exception {
    UserEnity user1 = userRepository.findByAge(10);
    System.out.println("第一次执行查询" + user1.getUsername());

    UserEnity user2 = userRepository.findByAge(10);
    System.out.println("第二次执行查询" + user2.getUsername());

    user1.setUsername("bbb");
    userRepository.save(user1);
    UserEnity user3 = userRepository.findByAge(10);
    System.out.println("第三次执行查询" + user3.getUsername());
  }

}
