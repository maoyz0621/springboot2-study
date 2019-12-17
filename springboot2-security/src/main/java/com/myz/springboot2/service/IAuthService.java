package com.myz.springboot2.service;

import com.myz.springboot2.pojo.User;

/**
 * 登录时要生成token，完成Spring Security认证，然后返回token给客户端
 * 注册时将用户密码用BCrypt加密，写入用户角色，由于是开放注册，所以写入角色系统控制，将其写成 ROLE_USER
 * 提供一个可以刷新token的接口 refresh 用于取得新的token
 *
 * @author maoyz
 */
public interface IAuthService {

    User register(User userToAdd);

    String login(String username, String password);

    String refresh(String oldToken);
}
