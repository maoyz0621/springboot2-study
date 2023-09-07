/**
 * Copyright 2023 Inc.
 **/
package com.myz.dubbo.core;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author maoyz0621 on 2023/7/14
 * @version v1.0
 */
@Data
@Accessors(chain = true)
public class UserContext implements Serializable {
    private static final long serialVersionUID = 2372825424105480608L;
    private String userName;
}