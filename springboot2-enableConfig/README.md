配置是否开启注解功能

- 1、实现**ImportSelector**接口注入
- 2、实现**ImportBeanDefinitionRegistrar**接口注入，最灵活
- 3、直接引入配置类，可以直接将类作为配置类，具有和注解`@Configuration` 相似的作用，可以在配置类中，定义 `@Bean` 注解，定义新的Bean