# 使用方法
1. 引入依赖
注意：redis的starter依赖是由springboot官方提供，从命名就可以看出来
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
2. 配置Redis
```yaml
spring:
  datasource:
    redis:
    host: 192.168.56.128
    password: 123456
```

3. 使用RedisTemplate或者StringRedisTemplate对象


# 自动配置类分析
* 配置信息类是RedisProperties.class， prefix = "spring.redis"
* 会导入两个连接redis的类@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
lettuce是新的连接方式，jedis是过去常用的
* RedisTemplate<Object, Object>，StringRedisTemplate分别是两个template，StringRedisTemplate相当于
  RedisTemplate<String, String>
```text
@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
public class RedisAutoConfiguration {
    public RedisAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"redisTemplate"}
    )
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

```