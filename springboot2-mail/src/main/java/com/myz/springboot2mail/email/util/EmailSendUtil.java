/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mail.email.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 发送邮件
 * 由于Spring Boot的starter模块提供了自动化配置，所以在引入了spring-boot-starter-mail依赖之后，
 * 会根据配置文件中的内容去创建JavaMailSender实例，因此我们可以直接在需要使用的地方直接@Autowired来引入邮件发送对象
 *
 * @author maoyz on 2018/8/27
 * @version: v1.0
 */
@Component
@Slf4j
public class EmailSendUtil {

  private static JavaMailSender mailSender;

  private static String from;

  private static Configuration configuration;

  @Autowired
  public void setConfiguration(Configuration configuration) {
    EmailSendUtil.configuration = configuration;
  }

  @Autowired
  public void setMailSender(JavaMailSender mailSender) {
    EmailSendUtil.mailSender = mailSender;
  }

  @Value("${spring.mail.username}")
  public void setFrom(String from) {
    EmailSendUtil.from = from;
  }


  /**
   * 发送简单邮件
   */
  public static void sendSimpleEmial(String[] to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    // 主题
    message.setSubject(subject);
    // 正文内容
    message.setText(text);

    try {
      mailSender.send(message);
      log.info("发送邮件成功");
    } catch (MailException e) {
      log.error("发送邮件失败");
    }
  }


  /**
   * 复杂邮件发送
   */
  public static void sendAttachmentsMail(String[] to, String subject, String text, String path) throws Exception {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(from);
    helper.setTo(to);
    // 主题
    helper.setSubject(subject);
    // 正文内容
    helper.setText(text);

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
  }


  /**
   * 嵌入静态资源
   */
  public static void sendInlineMail(String[] to, String subject, String text, String path) throws Exception {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);

    path = path.trim();
    // 截取最后文件名称
    String fileName = path.substring((path).lastIndexOf("\\") + 1);

    // 正文静态资源内容
    helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:"
            + fileName
            + "'></body></html>", true);
    FileSystemResource file = new FileSystemResource(new File(path));
    // addInline函数中资源名称 fileName 需要与正文中 cid:fileName 对应起来
    helper.addInline(fileName, file);

    try {
      mailSender.send(mimeMessage);
      log.info("发送邮件成功");
    } catch (MailException e) {
      log.error("发送邮件失败");
    }

  }

  /**
   * 模板邮件发送
   *
   * @throws Exception
   */
  public static void sendTemplateMail(String[] to, String subject, String username) throws Exception {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);

    Map<String, Object> model = new ConcurrentHashMap<>();
    model.put("username", username);

    String html = getHtml(model);

    helper.setText(html, true);

    try {
      mailSender.send(mimeMessage);
      log.info("发送邮件成功");
    } catch (MailException e) {
      log.error("发送邮件失败");
    }
  }

  private static String getHtml(Map<String, Object> model) throws IOException, TemplateException {
    Template template = configuration.getTemplate("email.ftl");
    return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
  }

}
