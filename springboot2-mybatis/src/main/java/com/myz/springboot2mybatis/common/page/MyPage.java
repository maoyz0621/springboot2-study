/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.page;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 包装返回给前台的信息，包括本次查询的状态码、错误信息、记录总数和数据列表
 *
 * @author maoyz0621 on 19-1-13
 * @version: v1.0
 */
@ApiModel(value = "返回给前台的信息，包括本次查询的状态码、错误信息、记录总数和数据列表")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyPage<T> implements Serializable {

    private static final long serialVersionUID = -5757190116216709796L;

    @ApiModelProperty(value = "状态码，默认状态")
    @JSONField(ordinal = 1)
    private Integer code = 200;

    @ApiModelProperty(value = "提示消息或者错误消息")
    @JSONField(ordinal = 2)
    private String message = "";

    @ApiModelProperty(value = "请求的唯一标识，预留")
    @JSONField(ordinal = 3)
    private String apiId = "";

    @ApiModelProperty(value = "记录总数")
    @JSONField(ordinal = 4)
    private Integer totalCount = 0;

    @ApiModelProperty(value = "本次返回的数据列表")
    @JSONField(ordinal = 5)
    private List<T> rows = Collections.emptyList();

}
