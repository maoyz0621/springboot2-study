package com.myz.springboot2mail.email.queue;

import com.myz.springboot2mail.email.model.Email;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 邮件队列,使用单例模式
 *
 * @author maoyz0621 on 2018/12/25
 * @version: v1.0
 */
public class MailQueue {

    private static final int QUEUE_MAX_SIZE = 1000;

    private static BlockingQueue<Email> queue = new LinkedBlockingDeque<>(QUEUE_MAX_SIZE);

    private MailQueue() {
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static MailQueue queue = new MailQueue();
    }

    /**
     * 单例队列
     */
    public static MailQueue getMailQueue() {
        return SingletonHolder.queue;
    }

    /**
     * 生产入队
     */
    public void produce(Email mail) throws InterruptedException {
        queue.put(mail);
    }

    /**
     * 消费出队
     */
    public Email consume() throws InterruptedException {
        return queue.poll();
    }

    /**
     * 获取队列大小
     */
    public int size() {
        return queue.size();
    }
}
