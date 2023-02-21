# 相同点
* 都用使用@Configuration 注解.可以加 ·@Bean、@Import、@ImportResource.
* 用@Condition 来控制加载条件

# 不同点
## 使用方式
@Configuration –Application的用户，直接代码进行配置的。
AutoConfiguration 是给 Springboot 插件（xxxx.xxx.starter）使用用的。

## 加载方式
* @Configuration加载是由@ComponentScan指定的package，未指定 以ApplicationClass 所属package开始。
* AutoConfiguration 是通过classpath*:META-INF/spring.factories来被发现。
  通过 key org.springframework.boot.autoconfigure.EnableAutoConfiguration. 
  AutoConfiguration 是由 import selector 的方式加载的
* @Configuration先于AutoConfiguration加载

# 补充
* AutoConfiguration可以使用@AutoConfigureOrder或者 @AutoConfigureBefore、@AutoConfigureAfter 作为注解
* AutoConfiguration的class所属包在@ComponentScan，被认为既是Configuration，又是AutoConfiguration。
所以会被加载两次。先以Configuration身份时先加载。因此@AutoConfigureBefore、@AutoConfigureAfter 不起作用。
* AutoConfiguration定义了BeanPostProcessor, BeanPostProcessor是Spring IOC容器给我们提供的一个扩展接口
  --> Spring IOC容器实例化Bean
  --> 调用BeanPostProcessor的postProcessBeforeInitialization方法
  --> 调用bean实例的初始化方法
  --> 调用BeanPostProcessor的postProcessAfterInitialization方法
  Spring容器通过BeanPostProcessor给了我们一个机会对Spring管理的bean进行再加工

* AutoConfiguration 使用ImportBeanDefinitionRegistrar 
* AutoConfiguration 使用ImportSelector