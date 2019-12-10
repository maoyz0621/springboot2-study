admin分为admin-server和admin-client两个项目工程, 其中admin-client收集endpoint信息注册到admin-server, 登录admin-server端可以查看endpoint暴露的信息

eureka-springboot2-admin: 使用eureka注册中心, springboot2-admin-eureka-server从注册中心拉取注册信息, springboot2-admin-eureka-client无需依赖spring-boot-admin-starter-client, admin整合spring-boot-starter-security实现登录权限