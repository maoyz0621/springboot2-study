/**
 * service 层
 * 业务适配调用层
 * 对vo数据的适配处理以及executor层的协调使用
 * <p>
 * 1、事务开启
 * 2、调用Executor层
 * 3、业务的拓展，在service适配
 * 4、多个Executor层编排
 * <p>
 * Executor层代表不同的业务指令，当需要多次执行时，在Service层完成
 *
 * @author maoyz
 */
package com.myz.project.domain.service;