/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author maoyz0621 on 2022/5/14
 * @version v1.0
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Addr {

    private Long id;

    private String type;
}