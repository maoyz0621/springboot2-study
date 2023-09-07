/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2value.config.model;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author maoyz0621 on 2023/7/4
 * @version v1.0
 */
@Data
public class SecurityMaskConfigModel {

    private String tableName;

    private List<ColumnModel> columnModelList;

    public static List<SecurityMaskConfigModel> convert(String config) {
        if (StringUtils.isEmpty(config)) {
            return Lists.newLinkedList();
        }
        String[] split = config.split("\\|");
        // private static final Pattern compile = Pattern.compile("[\\|]");
        // Matcher matcher = compile.matcher(securityMaskConfig);
        // List<String> collect = matcher.results().map(MatchResult::group).collect(Collectors.toList());
        if (ArrayUtils.isEmpty(split)) {
            return Lists.newLinkedList();
        }

        List<SecurityMaskConfigModel> maskConfigModelList = Lists.newLinkedList();
        for (String val : split) {
            // user=MOBILE_NUM:name,MOBILE_NUM:name
            String[] split1 = val.split("\\=");
            SecurityMaskConfigModel securityMaskConfigModel = new SecurityMaskConfigModel();
            securityMaskConfigModel.setTableName(split1[0]);
            String[] split2 = split1[1].split("\\,");
            List<SecurityMaskConfigModel.ColumnModel> columnModelList = Lists.newLinkedList();
            securityMaskConfigModel.setColumnModelList(columnModelList);
            for (String column : split2) {
                String[] split3 = column.split("\\:");
                SecurityMaskConfigModel.ColumnModel columnModel = new SecurityMaskConfigModel.ColumnModel();
                columnModel.setColumnName(split3[0]);
                columnModel.setConvertType(split3[1]);
                columnModelList.add(columnModel);
            }

            maskConfigModelList.add(securityMaskConfigModel);
        }
        return maskConfigModelList;
    }

    @Data
    public static class ColumnModel {
        private String columnName;

        private String convertType;
    }
}