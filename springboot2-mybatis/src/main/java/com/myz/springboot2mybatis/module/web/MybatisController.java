/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.module.web;

import com.myz.springboot2mybatis.common.page.MyPage;
import com.myz.springboot2mybatis.common.page.query.ConcreteQuery;
import com.myz.springboot2mybatis.common.util.Model2DtoUtil;
import com.myz.springboot2mybatis.module.dto.UserDto;
import com.myz.springboot2mybatis.module.entity.UserEntity;
import com.myz.springboot2mybatis.module.service.IPageHelperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


/**
 * @author maoyz on 2018/8/21
 * @version: v1.0
 */
@RestController
@RequestMapping("/mybatis")
@Api(value = "首次使用Swagger整合Controller", tags = "1.0")
public class MybatisController {

    @Autowired
    private IPageHelperService pageHelperService;


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


    /**
     * 根据ID查询用户
     */
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserDto getUserById(@PathVariable(value = "id") Integer id) {
       return Model2DtoUtil.model2Dto(pageHelperService.selectByPrimaryKey(id), UserDto.class);
    }

    /**
     * 查询用户列表
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDto> getUserList() {
        return Model2DtoUtil.model2Dto(pageHelperService.selectAll(null), UserDto.class);
    }

    /**
     * 查询用户列表
     */
    @ApiOperation(value = "获取用户列表，分页显示", notes = "获取用户列表，分页显示")
    @ApiImplicitParam(name = "qry", value = "分页信息", required = true, dataType = "ConcreteQuery", paramType = "body")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public MyPage<UserDto> getUserListsWithPage(@RequestBody ConcreteQuery qry) {
        MyPage<UserDto> page = Model2DtoUtil.model2Dto(pageHelperService.selectAllWithPage(qry), UserDto.class);
        page.setMessage(getLocalInfo());
        return page;
    }

    /**
     * 添加用户
     * <p>
     * 当请求参数中加了@RequestBody，需要设置paramType = "body"
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户", produces = "application/json")
    @ApiImplicitParam(name = "dto", value = "用户详细实体user", required = true, dataType = "UserDto", paramType = "body")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public int add(@RequestBody UserDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(dto.getUserId());
        userEntity.setAge(dto.getAge());
        userEntity.setUsername(dto.getUsername());
        userEntity.setBirthday(dto.getBirthday());

        return pageHelperService.insertSelective(userEntity);

    }

    /**
     * 根据id删除用户
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public int delete(@PathVariable(value = "id") Integer id) {
        return pageHelperService.deleteByPrimaryKey(id);
    }

    /**
     * 根据id修改用户信息
     * <p>
     * 当请求参数中加了@RequestBody，需要设置paramType = "body"
     */
    @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "dto", value = "用户实体user", required = true, dataType = "UserDto", paramType = "body")
    })
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public int update(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        UserEntity userEntity = Model2DtoUtil.model2Dto(dto, UserEntity.class);
        userEntity.setUserId(Math.toIntExact(id));
        return pageHelperService.updateByPrimaryKey(userEntity);
    }

    private String getLocalInfo(){
        StringBuilder sb = new StringBuilder();
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            sb.append("server info :")
                    .append("[ip:").append(inetAddress.getHostAddress()).append(",hostname:").append(inetAddress.getHostName())
                    .append("]");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
