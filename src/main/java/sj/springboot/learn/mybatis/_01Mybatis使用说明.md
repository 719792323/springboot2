[mybatis的官方地址](https://github.com/mybatis)

# 使用方案
1. 引入依赖
```xml

<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
```
2. 设置properties（数据库配置、mybatis配置）
3. 编写Dao、Service
4. 配置mapper.xml


# yaml中可配置的内容
```yaml
mybatis:
  # 如果需要以xml的方式配置mybatis，可以指定文件，通常在properties里配置，不在xml里配置
  # 如果同时在xml和properties中配置可能会产生配置冲突
  #  config-location: classpath:mybatis/mybatis-config.xml
  # 指定mapper文件位置
  mapper-locations: classpath:mybatis/mapper/*.xml
  type-aliases-package: sj.springboot.learn.mybatis
  configuration:
    #开启驼峰规则。数据库中通常user_id，对应java中userId，如果不开启那么无法将user_id绑定到userId中
    map-underscore-to-camel-case: true
```

# Mybatis注解
## Dao层使用的注解
* @MapperScan("mapper接口的包路径")，该注解放在SpringApplication上，则指定包路径下的接口可以不用加@Mapper
* @Mapper用在Dao接口上
* @Select、@Insert、@Update、@Delete
简单的sql可以直接在接口方法打上@Select或者其他标签，而不用在xml编写
如下getAllEntities方法所示
```text
@Mapper
public interface Dao {
    Entity getEntityById(Integer id);
    
    @Select("select * from payment")
    List<Entity> getAllEntities();
}
```


## Service
@Service用在ServiceImpl类上

# Mapper.xml中标签和属性


# 自动配置相关
@EnableConfigurationProperties({MybatisProperties.class})指定了配置属性类是MybatisProperties
而如下知道MybatisProperties的前缀是mybatis
@ConfigurationProperties(
prefix = "mybatis"
)
public class MybatisProperties {
}
```text
    @Configuration
    @ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
    @ConditionalOnSingleCandidate(DataSource.class)
    @EnableConfigurationProperties({MybatisProperties.class})
    @AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class})
    public class MybatisAutoConfiguration implements InitializingBean {
    
    }
```

# @Mapper接口原理
在Mybatis中有如下方法，是负责实例化@Mapper接口到类对象
```text
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
  
  }
```
具体分析如下
[mapper实例化分析](https://cloud.tencent.com/developer/article/2039071)