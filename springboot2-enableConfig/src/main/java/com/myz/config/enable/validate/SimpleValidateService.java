/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.validate;

import com.myz.config.enable.lock.DistributedLock;

/**
 * @author maoyz0621 on 2021/2/4
 * @version v1.0
 */
public class SimpleValidateService implements ValidateService {

    @DistributedLock(name = "lock")
    @Override
    public boolean valid() {
        System.out.println("============= SimpleValidateService valid =============");
        return false;
    }
}