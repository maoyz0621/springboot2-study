package com.myz.exception;


import com.example.dto.ResultEnum;

/**
 * 自定义异常
 *
 * @author maoyz on 18-3-11.
 */
public class EmpException extends RuntimeException {

    private int code;

    public EmpException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
