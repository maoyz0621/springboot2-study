/**
 * Copyright 2020 Inc.
 **/
package com.myz.dubbo.core.dubbo;

import lombok.Data;

/**
 * @author maoyz0621 on 2020/11/18
 * @version v1.0
 */
public class ManagerHolder {

    private static InheritableThreadLocal<Manager> MANAGER_HOLDER = new InheritableThreadLocal<>();

    /**
     * 绑定数据源
     * <p>
     * 需在调用服务层之前进行绑定
     */
    public static void putManager(Manager manager) {
        MANAGER_HOLDER.set(manager);
    }

    /**
     * 获取数据源
     */
    public static Manager getManager() {
        return MANAGER_HOLDER.get();
    }

    /**
     * 清除
     */
    public static void clearManager() {
        MANAGER_HOLDER.remove();
    }

    @Data
    public static class Manager {

        private String username;
        private Integer age;
        private String email;
        private String address;
    }
}