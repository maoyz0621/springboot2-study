/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.validate;

/**
 * @author maoyz0621 on 2021/2/4
 * @version v1.0
 */
public class SimpleValidateService implements ValidateService {

    @Override
    public boolean valid() {
        System.out.println("============= SimpleValidateService valid =============");
        return false;
    }
}