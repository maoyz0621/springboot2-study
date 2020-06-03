/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.entity.datatable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * datatable返回值
 *
 * @author maoyz on 2018/7/24
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnedData<T> implements Serializable {
    /**
     * 必要。上面提到了，Datatables发送的draw是多少那么服务器就返回多少。
     * 这里注意，作者出于安全的考虑，强烈要求把这个转换为整形，即数字后再返回，而不是纯粹的接受然后返回，这是 为了防止跨站脚本（XSS）攻击。
     */
    private Integer draw;
    /**
     * 必要。即没有过滤的记录数（数据库里总共记录数）
     */
    private Integer recordsTotal;
    /**
     * 必要。过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
     */
    private Integer recordsFiltered;
    /**
     * 必要。表中中需要显示的数据。
     */
    private List<T> data;
    /**
     * 可选。你可以定义一个错误来描述服务器出了问题后的友好提示
     */
    private String error;

}
