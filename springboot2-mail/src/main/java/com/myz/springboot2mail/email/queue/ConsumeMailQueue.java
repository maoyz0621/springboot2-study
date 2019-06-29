/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mail.email.queue;

import com.myz.springboot2mail.email.model.Email;
import com.myz.springboot2mail.email.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费邮件队列
 *
 * @author maoyz0621 on 2018/12/25
 * @version: v1.0
 */
@Slf4j
@Component
public class ConsumeMailQueue {

    @Autowired
    private IMailService mailService;

    @PostConstruct
    public void startThread() {
        log.debug("ConsumeMailQueue startThread()");

        // 创建定长线程池4
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        threadPool.submit(new PollMail(mailService));
        threadPool.submit(new PollMail(mailService));
    }

    class PollMail implements Runnable {

        @Autowired
        private IMailService mailService;

        public PollMail(IMailService mailService) {
            this.mailService = mailService;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Email email = MailQueue.getMailQueue().consume();
                    log.debug("ConsumeMailQueue start send email");

                    if (null != email) {
                        log.info("剩余邮件总数:{}", MailQueue.getMailQueue().size());

                        mailService.send(email);
                    }
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        }
    }

    @PreDestroy
    public void stopThread() {
        log.info("ConsumeMailQueue stopThread()");
    }
}
