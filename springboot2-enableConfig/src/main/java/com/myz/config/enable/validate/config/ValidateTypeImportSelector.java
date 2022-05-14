/**
 * Copyright 2022 Inc.
 **/
package com.myz.config.enable.validate.config;

import com.myz.config.enable.validate.CoreValidateService;
import com.myz.config.enable.validate.SimpleValidateService;
import com.myz.config.enable.validate.ValidateType;

/**
 * @author maoyz0621 on 2022/5/14
 * @version v1.0
 */
public class ValidateTypeImportSelector extends ValidateImportSelector<EnableValidate> {

    @Override
    protected String[] selectImports(ValidateType validateType) {
        switch (validateType) {
            case SIMPLE:
                return new String[]{SimpleValidateService.class.getName()};
            case CORE:
                return new String[]{CoreValidateService.class.getName()};
            default:
                return null;
        }
    }
}