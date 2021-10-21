/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.es.data;

import com.myz.springboot2elasticsearch.es.constants.BizEsConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author maoyz0621 on 2021/7/31
 * @version v1.0
 */
@Setter
@Getter
@Document(indexName = BizEsConstant.PRE + "123")
abstract class EsIndex {
}