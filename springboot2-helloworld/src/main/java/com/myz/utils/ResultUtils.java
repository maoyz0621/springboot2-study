package com.myz.utils;

import com.example.dto.Result;
import com.example.dto.ResultEnum;

/**
 * 为了输出格式统一格式
 *
 * @author maoyz on 18-3-11.
 */
public class ResultUtils {

    /**
     * 成功
     */
    public static Result success(Object obj) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(obj);
        return result;
    }

    /**
     * 返回自定义错误信息
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 重载,传入ResultEnum
     */
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(null);
        return result;
    }
}
