package com.myz.handler;


import com.myz.dto.Result;
import com.myz.dto.ResultEnum;
import com.myz.exception.EmpException;
import com.myz.utils.ResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author maoyz on 18-1-3.
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> exceptionHandler() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 505);
        map.put("msg", "buildFailure result");
        return map;
    }

    @ResponseBody
    @ExceptionHandler(ArithmeticException.class)
    public String exceptionHandler1() {
        return "buildFailure message";
    }

    @ResponseBody
    @ExceptionHandler(EmpException.class)
    public Result resultexceptionHandler(Exception e) {
        //　判断类型
        if (e instanceof EmpException) {
            EmpException empException = (EmpException) e;
            return ResultUtils.error(empException.getCode(), empException.getMessage());
        }
        return ResultUtils.error(ResultEnum.ERROR);
    }
}
