/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.module.web;


import com.myz.log.aop.module.model.SysModel;
import com.myz.log.aop.module.service.ISysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2018/12/29
 * @version: v1.0
 */
@RestController
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private ISysService sysService;

    @PostMapping("/delete")
    public void deleteById(String id, String a) {
        sysService.deleteById(id, a);
    }

    @PostMapping("/save")
    public int save(SysModel sysModel) {
        sysService.save(sysModel);
        return 0;
    }

    @PostMapping("/update")
    public void update(SysModel sysModel) {
        sysService.update(sysModel);
    }

    @GetMapping("/query")
    public void queryById(String id) {
        sysService.queryById(id);
    }
}
