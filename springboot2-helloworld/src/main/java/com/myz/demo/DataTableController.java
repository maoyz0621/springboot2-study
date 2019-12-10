package com.myz.demo;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.EmpEntity;
import com.example.entity.datatable.DataTable;
import com.example.entity.datatable.DataTableParameter;
import com.example.entity.datatable.ReceivedData;
import com.example.entity.datatable.ReturnedData;
import com.example.service.DataTableService;
import com.example.service.EmpMybatisService;
import com.example.service.GetJsonMapService;
import com.example.utils.MapConvertBeanUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author maoyz
 */
@Controller
public class DataTableController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataTableService dataTableService;

    @Autowired
    private EmpMybatisService empMService;
    @Autowired
    private GetJsonMapService jsonMapService;

    /**
     * 以"fnServerData": retrieveData形式发送ajax请求
     *
     * @param data
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/emp/list", method = RequestMethod.GET)
    @ResponseBody
    public String list(String data, HttpServletResponse response) throws IOException {
        DataTableParameter dataTableParam = getDataTableParameterByJsonParam(data);
        //
        PageInfo pageInfo = dataTableService.list(dataTableParam);
        // dataTable模型
        DataTable<PageInfo> dataTable = new DataTable<>();
        // 每次请求加1
        int sEcho = dataTableParam.getSEcho() + 1;
        dataTable.setAaData(pageInfo.getList());
        dataTable.setSEcho(sEcho);
        dataTable.setITotalDisplayRecords((pageInfo.getTotal()));
        dataTable.setITotalRecords(pageInfo.getTotal());
        dataTable.setError("404错误");
        response.setCharacterEncoding("utf-8");
        return JSONObject.toJSONString(dataTable);
    }

    /**
     * 以table.dataTable形式发送ajax请求
     *
     * @param data
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/emp/list1", method = RequestMethod.GET)
    @ResponseBody
    public String dtList(String data, HttpServletResponse response) throws IOException {
        ReceivedData receivedData = getReceiveDataByJsonParam(data);
        PageInfo pageInfo = dataTableService.list(receivedData);
        // dataTable模型
        ReturnedData<PageInfo> result = new ReturnedData<>();
        // 每次请求加1
        int draw = receivedData.getDraw() + 1;
        result.setDraw(draw);
        result.setRecordsTotal((int) pageInfo.getTotal());
        result.setData(pageInfo.getList());
        result.setError("404错误");
        result.setRecordsFiltered((int) pageInfo.getTotal());
        response.setCharacterEncoding("utf-8");
        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value = "/emp/add", method = RequestMethod.POST)
    @ResponseBody
    public String insert(EmpEntity emp) {
        empMService.insert(emp);
        return "aaaa";
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/emp/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(HttpServletRequest request) throws Exception {
        Map dataMap = jsonMapService.fromJsonToMap(request);
        empMService.update(dataMap);
        return "aaaa";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/emp/del", method = RequestMethod.POST)
    @ResponseBody
    public String del(HttpServletRequest request) throws Exception {
        Map dataMap = jsonMapService.fromJsonToMap(request);
        empMService.del(dataMap);
        return "aaaa";
    }

    /**
     * 将数组转Map
     */
    protected Map<String, Object> covertArrayJsonStringToMap(String jsonParam) {
        JSONArray jsonArray = JSONArray.parseArray(jsonParam);
        Map<String, Object> map = Maps.newConcurrentMap();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            map.put(jsonObj.getString("name"), jsonObj.get("value") != null ? jsonObj.get("value") : "");
        }
        logger.debug("array convert to map {} :" + map);
        return map;
    }

    /**
     * 将json转Map
     */
    protected Map<String, Object> covertJsonStringToMap(String jsonParam) {
        Map<String, Object> dataMap = JSONObject.parseObject(jsonParam, Map.class);
        logger.debug("json convert to map {} :" + dataMap);
        return dataMap;
    }

    /**
     * 转为DataTableParameter实体类
     */
    protected DataTableParameter getDataTableParameterByJsonParam(String jsonParam) {
        Map<String, Object> map = covertArrayJsonStringToMap(jsonParam);
        int sEcho = (int) map.get("sEcho");
        int iColumns = (int) map.get("iColumns");
        String sColumns = (String) map.get("sColumns");
        int iDisplayStart = (int) map.get("iDisplayStart");
        int iDisplayLength = (int) map.get("iDisplayLength");
        int iSortingCols = (int) map.get("iSortingCols");

        // 注意(,,)最后一位,缺失
        String[] sColumn = sColumns.split("\\,", -1);

        // 列名称及是否排序
        List<String> mDataProps = Lists.newArrayList();
        List<Boolean> bSortables = Lists.newArrayList();
        for (int i = 0; i < iColumns; i++) {
            String dataProp = (sColumn[i] != null) ? sColumn[i].trim() : null;
            Boolean sortable = (Boolean) map.get("bSortable_" + i);
            mDataProps.add(dataProp);
            bSortables.add(sortable);
        }

        // 排序类的编号、名称、方式
    /*{"name": "iSortCol_0","value": 1}, //第一个排序，value值得是第几列
    {"name": "sSortDir_0","value": "asc"},  //排序是正序还是倒叙
    {"name": "iSortingCols","value": 1}]*/ //一共有几列排序
        List<Integer> iSortCols = Lists.newArrayList();
        List<String> iSortColsName = Lists.newArrayList();
        List<String> sSortDirs = Lists.newArrayList();
        for (int i = 0; i < iSortingCols; i++) {
            Integer sortCol = (Integer) map.get("iSortCol_" + i);
            String sortColName = sColumn[i].trim();
            String sortDir = (String) map.get("sSortDir_" + i);
            iSortCols.add(sortCol);
            sSortDirs.add(sortDir);
            iSortColsName.add(sortColName);
        }

        // 封装参数
        return new DataTableParameter(sEcho, iColumns, sColumns, iDisplayStart, iDisplayLength, null, mDataProps, bSortables, iSortCols, sSortDirs, iSortColsName, iSortingCols);
    }

    protected ReceivedData getReceiveDataByJsonParam(String jsonParam) {
        Map<String, Object> jsonMap = covertJsonStringToMap(jsonParam);
        int draw = (int) jsonMap.get("draw");
        int start = (int) jsonMap.get("start");
        int length = (int) jsonMap.get("length");
        String search = (jsonMap.get("search") == null) ? "" : String.valueOf(jsonMap.get("search"));
        return new ReceivedData(draw, start, length, search);
    }

}
