
自动装配
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
    </dependency>
```

When using @ConfigurationProperties it is recommended to add 'spring-boot-configuration-processor' to your classpath to generate configuration metadata; 
可以从被@ConfigurationProperties注解的节点轻松的产生自己的配置元数据文件

```
    <!-- @ConfigurationProperties annotation processing (metadata for IDEs) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
    </dependency>
```



```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure-processor</artifactId>
        <optional>true</optional>
    </dependency>
```

配置属性文件：XxxProperties.java

```
@ConfigurationProperties(prefix = XxxProperties.MYZ_PREFIX)
public class XxxProperties {
    
    public static final String MYZ_PREFIX = "myz";

    private String val;

    private boolean cache;
    /**
     * list属性
     */
    private List<String> sizes;

    /**
     * set属性
     */
    private Set<String> sets;

    /**
     * map属性
     */
    private Map<String, String> param;

    /**
     * class类
     */
    private Class<?> clazz;
    
    ...

}
```

自动配置文件XxxStarterAutoConfiguration.java

```
@Configuration
@EnableConfigurationProperties(value = XxxProperties.class)
@AutoConfigureAfter({BeforeStarterAutoConfiguration.class})
public class XxxStarterAutoConfiguration {

    private final XxxProperties properties;

    public MaoyzStarterAutoConfiguration(XxxProperties properties) {
        this.properties = properties;
    }

    /**
     * 说明: @ConditionalOnClass依赖中包含HaveClass.class, 当类路径下有指定的类的条件下
     */
    @Bean
    @ConditionalOnClass(HaveClass.class)
    public MaoyzService maoyzServiceHaveClass() {
        System.out.println("**************** ConditionalOnClass *****************");
        return new MaoyzService();
    }

    /**
     * 说明: @ConditionalOnBean, 项目中注入HaveBean这个bean时, 执行; 当容器里有指定的Bean的条件下
     */
    @Bean
    @ConditionalOnBean(HaveBean.class)
    public MaoyzService maoyzServiceHaveBean() {
        System.out.println("**************** ConditionalOnBean *****************");
        return new MaoyzService();
    }

    /**
     * 说明: @ConditionalOnMissingClass依赖中不包含HaveClass.class, 当类路径下没有指定的类的条件下
     */
    @Bean
    @ConditionalOnMissingClass("com.myz.maoyz.spring.boot.autoconfigure.bean.MissingClass")
    public MaoyzService maoyzServiceMissingClass() {
        System.out.println("**************** ConditionalOnMissingClass *****************");
        return new MaoyzService();
    }

    /**
     * 说明: @ConditionalOnMissingBean, 项目中没有注入HaveBean这个bean时, 执行; 当容器里没有指定的Bean的条件下
     */
    @Bean
    @ConditionalOnMissingBean(HaveBean.class)
    public MaoyzService maoyzServiceMissingBean() {
        System.out.println("**************** ConditionalOnMissingBean *****************");
        return new MaoyzService();
    }

}

```

@ConditionalOnClass依赖中包含HaveClass.class, 当类路径下有指定的类的条件下

@ConditionalOnBean, 项目中注入HaveBean这个bean时, 执行; 当容器里有指定的Bean的条件下

@ConditionalOnMissingClass依赖中不包含HaveClass.class, 当类路径下没有指定的类的条件下

@ConditionalOnMissingBean, 项目中没有注入HaveBean这个bean时, 执行; 当容器里没有指定的Bean的条件下
 
 
需要将XxxAutoConfiguration加入resources\META-INF\spring.factories

```
    # 自动装配
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=
    com.myz.maoyz.spring.boot.autoconfigure.XxxStarterAutoConfiguration,
    com.myz.maoyz.spring.boot.autoconfigure.XxxStarterAutoConfiguration
```


项目结构组成：

+ xxx-spring-boot
+ xxx-spring-boot-autoconfigure
+ xxx-spring-boot-starter

使用的xxx-spring-boot-starter