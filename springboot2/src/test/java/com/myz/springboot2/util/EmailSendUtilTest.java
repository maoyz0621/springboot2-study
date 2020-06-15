package com.myz.springboot2.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author maoyz on 2018/8/27
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSendUtilTest {

  @Test
  public void sendSimpleEmial() throws Exception {
    EmailSendUtil.sendSimpleEmial(new String[]{"245890416@qq.com"}, "主题：maoyz", "springboot email");
  }

  @Test
  public void sendAttachmentsMail() throws Exception {
    EmailSendUtil.sendAttachmentsMail(new String[]{"245890416@qq.com"}, "主题：maoyz附件", "springboot 附件 email", "F:\\IDEA\\springboot2\\src\\main\\resources\\static\\img\\weixin.jpg");
  }

  @Test
  public void sendInlineMail() throws Exception {
    EmailSendUtil.sendInlineMail(new String[]{"245890416@qq.com"}, "主题：maoyz附件", "", "F:\\IDEA\\springboot2\\src\\main\\resources\\static\\img\\weixin.jpg");

  }

  @Test
  public void sendTemplateMail() throws Exception {
    EmailSendUtil.sendTemplateMail(new String[]{"245890416@qq.com"}, "主题：模板邮件", "aaaa");

  }

}