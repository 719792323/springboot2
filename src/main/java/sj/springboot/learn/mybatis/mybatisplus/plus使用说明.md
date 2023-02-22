[plus官网](https://github.com/baomidou/mybatis-plus)
1. 安装 MybatisX插件
2. 导入依赖
```xml
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
        </dependency>
```
3. plus的自动配置原理
```text
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties({MybatisPlusProperties.class}) //注入MybatisPlusProperties配置类，prefix = "mybatis-plus"
@AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisPlusLanguageDriverAutoConfiguration.class})
public class MybatisPlusAutoConfiguration implements InitializingBean {
    private final MybatisPlusProperties properties;

    如下，@Bean提交对象时会自动注入SpringIOC中的DataSource，如druid或者harika
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    
    }
}
```
4. 使用plus提供的BaseMapper接口（看相关视频，先看完mybatis的再看plus的）

# 补充
1. properties中的mapperLocations设置是classpath*，*说明是任意包的classpath下，classpath只是
当前classpath下，所以plus扫描的范围更广，且不要自己去配置。
```text
public class MybatisPlusProperties {
    private String[] mapperLocations = new String[]{"classpath*:/mapper/**/*.xml"};
}
```
mapperLocations 自动配置好的。有默认值。classpath*:/mapper/**/*.xml；
任意包的类路径下的所有mapper文件夹下任意路径下的所有xml都是sql映射文件。  建议以后sql映射文件，放在 mapper下