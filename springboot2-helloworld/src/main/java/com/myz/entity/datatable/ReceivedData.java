/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.entity.datatable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * datatable发送参数
 *
 * @author maoyz on 2018/7/24
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedData<T> implements Serializable {
    /**
     * 绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）。
     * 要求在服务器接收到此参数后再返回（
     */
    private Integer draw;
    /**
     * 第一条数据的起始位置，比如0代表第一条数据
     */
    private Integer start;
    /**
     * 告诉服务器每页显示的条数，这个数字会等于返回的 data集合的记录数，可能会大于因为服务器可能没有那么多数据。
     * 这个也可能是-1，代表需要返回全部数据(尽管这个和服务器处理的理念有点违背)
     */
    private Integer length;
    /**
     * 全局的搜索条件，条件会应用到每一列（ searchable需要设置为 true ）
     */
    private String search;



/*

  search[regex]	booleanJS
  如果为 true代表全局搜索的值是作为正则表达式处理，为 false则不是。 注意：通常在服务器模式下对于大数据不执行这样的正则表达式，但这都是自己决定的

  order[i][column]	integerJS
  告诉后台那些列是需要排序的。 i是一个数组索引，对应的是 columns配置的数组，从0开始

  order[i][dir]	stringJS
  告诉后台列排序的方式， desc 降序 asc升序

  columns[i][data]	stringJS
  columns 绑定的数据源，由 columns.dataOption 定义。

  columns[i][name]	stringJS
  columns 的名字，由 columns.nameOption 定义。

  columns[i][searchable]	booleanJS
  标记列是否能被搜索,为true代表可以，否则不可以，这个是由 columns.searchableOption 控制

  columns[i][orderable]	booleanJS
  标记列是否能排序,为 true代表可以，否则不可以，这个是由 columns.orderableOption 控制

  columns[i][search][value]	stringJS
          标记具体列的搜索条件

  columns[i][search][regex]	booleanJS
  特定列的搜索条件是否视为正则表达式， 如果为 true代表搜索的值是作为正则表达式处理，为 false则不是。 注意：通常在服务器模式下对于大数据不执行这样的正则表达式，但这都是自己决定的
*/


}
