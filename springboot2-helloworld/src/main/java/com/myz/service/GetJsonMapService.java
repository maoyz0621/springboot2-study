/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 将接收的json转为Map
 *
 * @author maoyz on 2018/7/27
 * @version: v1.0
 */
@Service
public class GetJsonMapService {
    private Logger logger = LoggerFactory.getLogger(GetJsonMapService.class);

    /**
     * json转map
     *
     * @param request
     * @return
     */
    public Map fromJsonToMap(HttpServletRequest request) throws Exception {
        String data = request.getParameter("data");

        if (StringUtils.isEmpty(data)) {
            throw new Exception("request.getParameter(\"data\") is null");
        }
        ConcurrentMap jsonMap = JSONObject.parseObject(data, ConcurrentMap.class);

        logger.debug("jsonMap {}:" + jsonMap);

        return ((List<Map>) jsonMap.get("PARAM")).get(0);
    }

}
