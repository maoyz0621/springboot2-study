package com.myz.springboot2mail.email.service.impl;

import com.myz.springboot2mail.email.model.Email;
import com.myz.springboot2mail.email.service.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author maoyz0621 on 18-12-24
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private IMailService mailService;

    @Test
    public void send() throws Exception {
        Email email = new Email(new String[]{"245890416@qq.com"}, "主题：maoyz", "springboot email",null,null);
        mailService.send(email);
    }

    @Test
    public void sendHtml() {
    }

    @Test
    public void sendFreemarker() {
    }

    @Test
    public void sendQueue() {
    }

    @Test
    public void sendRedisQueue() {
    }

    @Test
    public void listMail() {
    }
}