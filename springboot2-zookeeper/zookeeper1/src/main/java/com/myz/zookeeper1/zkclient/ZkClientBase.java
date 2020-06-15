/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper1.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 *       <dependency>
*             <groupId>com.github.sgroschupf</groupId>
*             <artifactId>zkclient</artifactId>
*             <version>0.1</version>
*         </dependency>
 * @author maoyz on 2018/9/3
 * @version: v1.0
 */
public class ZkClientBase {

    private static final Logger logger = LoggerFactory.getLogger(ZkClientBase.class);

    private static final String CONNECTING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static final Integer TIMEOUT = 36000;

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(new ZkConnection(CONNECTING), TIMEOUT);

        // 递归删除deleteRecursive()
        zkClient.deleteRecursive("/zkclient");

        // 监控子节点的增加和删除
        zkClient.subscribeChildChanges("/zkclient", (parentPath, currentChilds) -> {
            logger.debug("监控子节点的增加和删除:" + parentPath + "-->" + currentChilds.toString());
        });

        // 监控节点数据的改变和删除
        zkClient.subscribeDataChanges("/zkclient", new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                logger.debug("监控节点数据的改变:" + dataPath + "==>" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                logger.debug("监控节点数据的删除:" + dataPath);
            }
        });

        // 递归创建节点，但不能赋值
        zkClient.createPersistent("/zkclient/zk1", true);

        TimeUnit.SECONDS.sleep(1);


        zkClient.writeData("/zkclient", "zkzk");
        TimeUnit.SECONDS.sleep(1);

        logger.debug("/zkclient = {}", (String) zkClient.readData("/zkclient"));


        zkClient.createPersistent("/zkclient/zk1/zzk1");
        TimeUnit.SECONDS.sleep(1);
        zkClient.createPersistent("/zkclient/zk1/zzk2");
        TimeUnit.SECONDS.sleep(1);

        // countChildren()子节点数
        logger.debug("/zkclient Children = {}", zkClient.countChildren("/zkclient"));
        logger.debug("/zkclient/zk1 Children = {}", zkClient.countChildren("/zkclient/zk1"));

        TimeUnit.SECONDS.sleep(1);

    }
}
