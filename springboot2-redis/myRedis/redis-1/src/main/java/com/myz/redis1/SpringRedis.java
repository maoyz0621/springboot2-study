/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.redis1;

import com.myz.redis1.dao.UserDao;
import com.myz.redis1.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * @author maoyz on 2018/4/21
 * @Version: v1.0
 */
public class SpringRedis {

    ApplicationContext context;

    private StringRedisTemplate stringRedisTemplate;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("classpath*:spring-redis.xml");
    }

    @Test
    public void test() {
        UserDao userDao = context.getBean(UserDao.class);
        User user1 = new User("m1", 12);
        User user2 = new User("m2", 13);
        userDao.save(user1);
        userDao.save(user2);
    }

    @Test
    public void testsSringRedisTemplate() {
        stringRedisTemplate = (StringRedisTemplate) context.getBean("stringRedisTemplate");
        stringRedisTemplate.opsForValue().set("s1", "你好");
    }

    @Test
    public void testCallback() {
        UserDao userDao = context.getBean(UserDao.class);
        userDao.useCallback();
    }
}
