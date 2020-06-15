/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.swagger.common.web;

import com.myz.swagger.common.module.JsonResult;
import com.myz.swagger.common.module.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author maoyz on 2018/8/21
 * @version: v1.0
 */
@RestController
@Api(value = "首次使用Swagger整合Controller", tags = "1.0")
public class SwaggerController {


    /**
     * 方法的说明 @ApiOperation()
     * 定义请求参数 @ApiImplicitParams()
     * <p>
     * paramType：参数放在哪个地方
     * § header-->请求参数的获取：@RequestHeader
     * § query-->请求参数的获取：@RequestParam
     * § path（用于restful接口）-->请求参数的获取：@PathVariable
     * § body（以流的形式提交 仅支持POST）
     * § form（以form表单的形式提交 仅支持POST）
     */
    @ApiOperation(value = "首页信息", notes = "首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "必传字段", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "用户名称", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String get(@PathVariable Integer id, @RequestParam(required = false) String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("id = ").append(id).append("; ").append("name = ").append(name);
        return sb.toString();
    }

    @ApiOperation(value = "首页信息", notes = "首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "必传字段", paramType = "query", required = true, dataType = "Integer"),
    })
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@RequestParam Integer id) {
        return "ID为:" + id;
    }


    // Restful 接口

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    /**
     * 根据ID查询用户
     */
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserById(@PathVariable(value = "id") Long id) {
        JsonResult<User> result = new JsonResult<>();
        try {
            User user = users.get(id);
            result.setResult(user);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setMessage(e.getClass().getName() + ":" + e.getMessage());
            result.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 查询用户列表
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList() {
        JsonResult<List<User>> result = new JsonResult<>();
        try {
            List<User> userList = new ArrayList<User>(users.values());
            result.setResult(userList);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setMessage(e.getClass().getName() + ":" + e.getMessage());
            result.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加用户
     *
     * 当请求参数中加了@RequestBody，需要设置paramType = "body"
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户", produces = "application/json")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User", paramType = "form")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody User user) {
        JsonResult<Long> result = new JsonResult<>();
        try {
            users.put(user.getId(), user);
            result.setResult(user.getId());
            result.setStatus("ok");
        } catch (Exception e) {
            result.setMessage(e.getClass().getName() + ":" + e.getMessage());
            result.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id删除用户
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Long id) {
        JsonResult<Long> result = new JsonResult<>();
        try {
            users.remove(id);
            result.setResult(id);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setMessage(e.getClass().getName() + ":" + e.getMessage());
            result.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id修改用户信息
     *
     * 当请求参数中加了@RequestBody，需要设置paramType = "form"
     */
    @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User", paramType = "form")
    })
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@PathVariable("id") Long id, @RequestBody User user) {
        JsonResult<User> result = new JsonResult<>();
        try {
            User u = users.get(id);
            u.setUsername(user.getUsername());
            u.setAge(user.getAge());
            users.put(id, u);
            result.setResult(u);
            result.setStatus("ok");
        } catch (Exception e) {
            result.setMessage(e.getClass().getName() + ":" + e.getMessage());
            result.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 使用@ApiIgnore该注解忽略这个API
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }
}
