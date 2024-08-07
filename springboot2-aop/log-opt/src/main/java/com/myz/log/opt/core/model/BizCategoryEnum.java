package com.myz.log.opt.core.model;

/**
 * @author dukui
 */
public enum BizCategoryEnum {

    CREATE("新增"),

    UPDATE("更新"),

    DELETE("删除"),

    FIND("查询"),

    PAUSE("暂停"),

    RESUME("恢复"),

    BIND("绑定"),

    UNBIND("解绑"),

    PLACE_ORDER("订购"),

    CANCEL_ORDER("退订"),

    RENEW_ORDER("续订"),

    ADJUST_RESOURCE("资源变更"),

    ACTIVATE("激活"),

    FREEZE("冻结"),

    IMPORT("导入"),

    EXPORT("导出"),

    LOGIN("登录"),

    LOGOUT("登出"),

    REGISTER_ACCOUNT("注册账号"),

    CANCEL_ACCOUNT("注销账号"),

    AUTHENTICATE("认证"),

    AUTHORIZE("授权");

    /**
     * Detailed description of the enumeration instance.
     */
    private final String description;

    BizCategoryEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
