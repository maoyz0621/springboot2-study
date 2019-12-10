访问：ip:port/actuator  GET请求

include: 可以查看到所有暴露信息  
exclude: 排除暴露endpoint

常用：

health: 健康检查

info: 配置在配置文件中以 info 开头的配置信息

beans: bean 的别名、类型、是否单例、类的地址、依赖等信息

conditions: 查看代码的某个配置在什么条件下生效，或者某个自动配置为什么没有生效

heapdump: 返回一个 GZip 压缩的 JVM 堆 dump

mappings: 描述全部的 URI 路径，以及它们和控制器的映射关系

threaddump: 当前线程快照