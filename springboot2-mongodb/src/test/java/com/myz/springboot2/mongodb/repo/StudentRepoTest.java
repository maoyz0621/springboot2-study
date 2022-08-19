package com.myz.springboot2.mongodb.repo;

import com.myz.springboot2.mongodb.Springboot2MongodbApplication;
import com.myz.springboot2.mongodb.entity.StudentEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author maoyz0621 on 2022/6/16
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot2MongodbApplication.class)
@SpringBootApplication
public class StudentRepoTest {

    @Autowired
    StudentRepo studentRepo;

    @Test
    public void insert() {
        for (int i = 30; i < 40; i++) {
            Long index = Long.parseLong(i + "");
            StudentEntity studentEntity = new StudentEntity()
                    .setId(index)
                    .setUsername("mao")
                    .setBirth(new Date())
                    .setHeight(10);
            studentRepo.insert(studentEntity);
        }

    }

    @Test
    public void update() {
        StudentEntity studentEntity = new StudentEntity()
                .setId(20L)
                .setUsername("mao20")
                .setBirth(new Date())
                .setHeight(10);
        studentRepo.update(studentEntity);
    }

    @Test
    public void save() {
        StudentEntity studentEntity = new StudentEntity()
                .setId(20L)
                .setUsername("mao20")
                .setBirth(new Date())
                .setHeight(20);
        studentRepo.save(studentEntity);
    }

    @Test
    public void delete() {
        studentRepo.delete(20L);
    }
}