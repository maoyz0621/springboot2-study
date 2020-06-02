```
com  
└── alibaba  
    └── cola  
        ├── assembler  \\提供Assembler标准  
        ├── boot \\这是框架的核心启动包，负责框架组件的注册、发现  
        ├── command  \\提供Command标准  
        ├── common  
        ├── context  \\提供框架执行所需要的上下文  
        ├── convertor  \\提供Convertor标准  
        ├── domain  \\提供Domain Entity标准  
        ├── event  
        ├── exception \\提供Exception标准  
        ├── extension  \\负责扩展机制中的重要概念-扩展(Extension)的定义和执行  
        ├── extensionpoint  \\负责扩展机制中的重要概念-扩展点(Extension Point)的定义  
        ├── logger  \\提供DIP的日志接口  
        ├── repository  \\提供仓储（Repository）的标准  
        ├── rule  \\提供业务规则或者叫策略（Rule）的标准和执行  
        │   └── ruleengine  
        └── validator  \\提供Validator标准和执行  
```      
      
一键构建项目:Archetype

    mvn archetype:generate  -DgroupId=com.alibaba.demo -DartifactId=demo -Dversion=1.0.0-SNAPSHOT -Dpackage=com.alibaba.demo -DarchetypeArtifactId=cola-framework-archetype-web -DarchetypeGroupId=com.alibaba.cola -DarchetypeVersion=2.0.0
  

模块：  
    生成了5个module，分别是demo-controller、demo-client、demo-app、demo-domain、demo-infrastructure。
    其中demo-client是用来存放RPC调用中的DTO（Data Transfer Object）类，
    其它四个module分别对应控制层、应用层、领域层和基础实施层