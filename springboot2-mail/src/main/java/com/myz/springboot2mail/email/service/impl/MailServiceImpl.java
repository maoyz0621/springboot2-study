/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mail.email.service.impl;

import com.myz.springboot2mail.common.model.Result;
import com.myz.springboot2mail.email.model.Email;
import com.myz.springboot2mail.email.queue.MailQueue;
import com.myz.springboot2mail.email.service.IMailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author maoyz0621 on 18-12-24
 * @version: v1.0
 */
@Service
public class MailServiceImpl implements IMailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration configuration;
    @Value("${server.path}")
    private String path;
    @Value("${spring.mail.username}")
    private String from;


    @Override
    public Boolean send(Email mail) throws Exception {
        log.debug("start send simple message");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail.getEmail());
        // 主题
        message.setSubject(mail.getSubject());
        // 内容
        message.setText(mail.getContent());

        try {
            mailSender.send(message);
            log.info("发送邮件成功");

            return Boolean.TRUE;
        } catch (MailException e) {
            log.error("发送邮件失败,{}", e);
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean sendHtml(Email mail) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from, "");
        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());

        path = path.trim();
        FileSystemResource file = new FileSystemResource(new File(path));
        // 截取最后文件名称
        String fileName = path.substring((path).lastIndexOf("\\") + 1);
        helper.addAttachment(fileName, file);

        try {
            mailSender.send(mimeMessage);
            log.info("发送邮件成功");
        } catch (MailException e) {
            log.error("发送邮件失败");
        }
        return true;
    }

    @Override
    public Boolean sendFreemarker(Email mail) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from, "Freemarker");
        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getSubject());

        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("mail", mail);
        model.put("path", path);

        String html = getHtml(model, mail);

        helper.setText(html, true);

        try {
            mailSender.send(mimeMessage);
            log.info("发送邮件成功");
            mail.setContent(html);
            //    保存发送的邮件

        } catch (MailException e) {
            log.error("发送邮件失败");
        }
        return true;

    }

    private String getHtml(Map<String, Object> model, Email email) throws IOException, TemplateException {
        Template template = configuration.getTemplate(email.getTemplate());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

    @Override
    public void sendQueue(Email mail) throws Exception {
        MailQueue.getMailQueue().produce(mail);
    }

    @Override
    public void sendRedisQueue(Email mail) throws Exception {

    }

    @Override
    public Result listMail(Email mail) {
        return null;
    }
}
