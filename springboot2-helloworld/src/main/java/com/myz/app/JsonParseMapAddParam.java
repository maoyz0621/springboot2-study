package com.myz.app;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author maoyz on 2018/4/16.
 */
public class JsonParseMapAddParam {

    public static void main(String[] args) {
        Map paramMap = new HashMap();
        paramMap.put("ACCT_ID", "aaaaaaaaaaaaaaaaaaaa");
        paramMap.put("UPDATE_USER_NAME", "maoyuezhong");
        paramMap.put("UPDATE_DATE", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

        Map<String, String> param1 = new HashMap<>();
        param1.put("MODIFY_TAG", "0");
        param1.put("ACCT_ID", "00000");
        param1.put("CATEGORY", "qa05");
        param1.put("TITLE", "asdas");

        Map<String, String> param2 = new HashMap<>();
        param2.put("MODIFY_TAG", "0");
        param2.put("ACCT_ID", "00000");
        param2.put("CATEGORY", "qa05");
        param2.put("TITLE", "asdas");

        List<Map<String, String>> list = new ArrayList<>();
        list.add(param1);
        list.add(param2);

        Map<String, List> tableMap = new HashMap();
        tableMap.put("TF_F_TICKET", list);

        Map<String, Map> map = new HashMap();
        map.put("private", tableMap);

        Map privateMap = map.get("private");

        // 迭代key
        Iterator paramIterator = privateMap.keySet().iterator();
        while (paramIterator.hasNext()) {
            // 转换成String
            String tableName = (String) paramIterator.next();
            // 获取table数据，是list类型
            List<Map<String, Object>> tableMapList = (List) privateMap.get(tableName);
            // 迭代list
            Iterator tableValue = tableMapList.iterator();
            while (tableValue.hasNext()) {
                // list里面存放的也是map值
                Map<String, Object> tableValueMap = (Map) tableValue.next();
                tableValueMap.putAll(paramMap);
            }
        }

        System.out.println(map);
    }
}
