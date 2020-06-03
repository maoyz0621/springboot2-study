package com.myz.dto;

/**
 * 状态码以及提示信息
 *
 * @author maoyz on 18-3-11.
 */
public enum ResultEnum {

    WRONG(0, "错误"), ERROR(-1, "系统错误"), SUCCESS(1, "成功"),

    AGE_LARGE(100, "大了"), AGE_SMALL(101, "小了");

    /**
     * 属性定义成final，天生不可变
     */
    private final int code;
    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static void main(String[] args) {
        // .values()
        for (ResultEnum arg : ResultEnum.values()) {
            System.out.println(arg);
        }
    }
}
