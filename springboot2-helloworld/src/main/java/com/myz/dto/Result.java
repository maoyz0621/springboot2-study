package com.myz.dto;

/**
 * 传输层
 *
 * @author maoyz on 18-3-11.
 */
public class Result<T> {

    /**
     * 　错误码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回值
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
