# 项目结构
通常定义两个项目如：my-starter和my-starer-autoconfigure
然后my-starer依赖于my-starer-autoconfigure（my-starter的pom.xml依赖于my-starer-autoconfigure）
也可以只定义一个项目。

# 必须导入的依赖关系
因为autoconfigure需要spring的一些注解所以必须依赖以下依赖包，version可根据情况修改
```xml
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>2.3.4.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
            <version>2.3.4.RELEASE</version>
        </dependency>
```

# 功能类、自动配置类、属性类的关系
1. 功能类
实现具体功能，一般不用标注任何注解，也就是不会无条件的被注入到spring的ioc容器中
如下：定义了一个IO功能类
```java
public class MyStream extends BufferedInputStream {
    public MyStream(InputStream inputStream) {
        super(inputStream);
    }
}
```
2. 自动配置类
以xxAutoConfiguration的名称命名，负责对功能类进行配置
搭配@Configuration、@Conditional系列、@EnableConfigurationProperties来使用
```java
@Configuration
@ConditionalOnMissingBean(BufferedInputStream.class)
@EnableConfigurationProperties(MySteamProperties.class) //将配置类自动注入到成员属性
public class MyAutoConfiguration {

    @Resource //这个注解按理来说可以不需要（未进行测试）
    private MySteamProperties steamProperties;

    @Bean
    public MyStream myStream() throws FileNotFoundException {
        return new MyStream(new BufferedInputStream(new FileInputStream(steamProperties.getFile())));
    }


}
```
3. 属性类
搭配@ConfigurationProperties注解，可以将properties中的属性自动注入，然后自动配置类利用配置类来实例化具体功能类
```java
@ConfigurationProperties(
        prefix = "stream"
)
public class MySteamProperties {
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
```
# 配置spring.factory
为了让xxAutoconfiguration能被springboot加载，必须在xxAutoconfiguration所在的项目的classpath下
定义一个META-INF包，并在内部定义spring.factories文件，文件中指定xxAutoconfiguration的包路径
```text
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  autoconfigure.MyAutoConfiguration
```

最后在所在项目引入starter依赖，就可以使用自定义starter