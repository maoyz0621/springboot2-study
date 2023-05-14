package com.myz.encrypt.common;

import com.myz.encrypt.config.annotation.EncryptFiled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 传输层
 *
 * @author maoyz on 18-3-11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @EncryptFiled(nesting = true)
    private T data;

    public static <T> Result<T> of(T t) {
        return new Result<>(1, "success", t);
    }
}
