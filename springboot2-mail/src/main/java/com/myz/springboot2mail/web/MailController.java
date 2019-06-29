/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mail.web;

import com.myz.springboot2mail.email.model.Email;
import com.myz.springboot2mail.email.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author maoyz0621 on 2018/12/25
 * @version: v1.0
 */
@Controller
public class MailController {

  @Autowired
  private IMailService mailService;

  @GetMapping("/get")
  @ResponseBody
  public String send() throws Exception {
    Email email = new Email(new String[]{"245890416@qq.com"}, "主题：maoyz", "springboot email",null,null);
    Boolean send = mailService.send(email);
    if (send){
      return "发送成功";
    } else {
      return "发送失败";
    }
  }
}
