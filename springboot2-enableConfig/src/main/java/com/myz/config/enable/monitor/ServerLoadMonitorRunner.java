/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.monitor;

import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
public class ServerLoadMonitorRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ServerLoadMonitorRunner.class);

    private static final OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    @Override
    public void run(String... args) throws Exception {
        log.info("ServerLoadMonitorRunner run ..........");
        try {
            collectSystemInfo();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void collectSystemInfo() {
        // 输出json格式的信息到文件
        try {
            long freePhysicalMemorySize = systemMXBean.getFreePhysicalMemorySize();
            double systemCpuLoad = systemMXBean.getSystemCpuLoad();
            String hostName = InetAddress.getLocalHost().getHostName();
            long freeSpace = File.listRoots()[0].getFreeSpace();

            ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();
            scheduledThreadPool.scheduleAtFixedRate(() -> {
                log.info("Collect System info: freePhysicalMemorySize={},\r\nsystemCpuLoad={},\r\nhostName={},\r\nfreePhysicalMemorySize={}\r\n", freePhysicalMemorySize, systemCpuLoad, hostName, freePhysicalMemorySize);
            }, 10, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("collectSystemInfo error:", e);
        }

        // scheduledThreadPool.shutdown();
    }
}