package com.myz.demo;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.service.DataTableService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 使用freemarker模板引擎，默认存放在src\main\resources\templates下
 *
 * @author maoyz
 */
@Controller
public class FreemarkerController {

    @Autowired
    private DataTableService chargeRuleService;

    /**
     * freemarker
     */
    @GetMapping(path = "/ftl")
    public String ftl(Model model) {
        model.addAttribute("name", "myz");
        model.addAttribute("sex", 2);
        List<String> userList = new ArrayList<>();
        userList.add("aaa");
        userList.add("bbb");
        userList.add("ccc");
        model.addAttribute("userList", userList);
        return "index";
    }

    /**
     * @param model
     * @return
     */
    @GetMapping(path = "/ftl.html")
    public String ftl1(Model model) {
        model.addAttribute("name", "myz");
        List<String> userList = new ArrayList<>();
        userList.add("aaa");
        userList.add("bbb");
        userList.add("ccc");
        model.addAttribute("userList", userList);
        model.addAttribute("title", "dataTable动态数据展示");
        return "dt";
    }


    /*[
    {"name": "sEcho","value": 1}, //请求次数
    {"name": "iColumns","value": 5}, //列数
    {"name": "sColumns","value": "ID,USERNAME,POSITION,SALARY,START_DATE"},
    {"name": "iDisplayStart""value": 0}, //记录起始，0表示第一条
    {"name": "iDisplayLength","value": 20}, //每页显示的记录数
    {"name": "mDataProp_0","value": "function"}, //第一列名称
    {"name": "bSortable_0","value": false}, //第一列是否可以排序
    {"name": "mDataProp_1","value": "function"}, //第二列名称
    {"name": "bSortable_1","value": true}, //第二列是否可以排序
    {"name": "mDataProp_2","value": "function"},
    {"name": "bSortable_2","value": true},
    {"name": "mDataProp_3","value": "function"},
    {"name": "bSortable_3","value": true},
    {"name": "mDataProp_4","value": "function"},
    {"name": "bSortable_4","value": true},
    {"name": "iSortCol_0","value": 1}, //第一个排序，value值得是第几列
    {"name": "sSortDir_0","value": "asc"},  //排序是正序还是倒叙
    {"name": "iSortingCols","value": 1}]*/ //一共有几列排序
    @RequestMapping("/tableAjax")
    @ResponseBody
    public Map tableDemoAjax(String data) {
        System.out.println(data);
        String sEcho = "";// 记录操作的次数  每次加1
        String iDisplayStart = "";// 起始
        String iDisplayLength = "";// size
        String sSearch = "";// 搜索的关键字
        int count = 0;  //查询出来的数量
        JSONArray ja = (JSONArray) JSONArray.parse(data);

        //分别为关键的参数赋值
        for (int i = 0; i < ja.size(); i++) {
            JSONObject obj = (JSONObject) ja.get(i);
            if (obj.get("name").equals("sEcho")) {
                sEcho = obj.get("value").toString();
            }
            if (obj.get("name").equals("iDisplayStart")) {
                iDisplayStart = obj.get("value").toString();
            }
            if (obj.get("name").equals("iDisplayLength")) {
                iDisplayLength = obj.get("value").toString();
            }
            if (obj.get("name").equals("sSearch")) {
                // 查询字段
                sSearch = obj.get("value").toString();
            }
        }

        Map dataMap = new HashMap();
        //为操作次数加1
        int initEcho = Integer.parseInt(sEcho) + 1;
        List list = Lists.newArrayList();
        for (int i = Integer.valueOf(iDisplayStart); i < (Integer.valueOf(iDisplayStart) + Integer.valueOf(iDisplayLength)); i++) {
            Map map = Maps.newConcurrentMap();
            map.put("userId", "20180720" + i);
            map.put("username", "222222");
            map.put("position", 1);
            map.put("salary", 1);
            map.put("startDate", new Date());
            list.add(map);
        }
        dataMap.put("iTotalRecords", list.size());
        dataMap.put("sEcho", sEcho);
        dataMap.put("iTotalDisplayRecords", 50);
        dataMap.put("aaData", list);
        return dataMap;
    }

}
