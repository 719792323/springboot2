在Resource下创建application.properties
或者
在Resource下创建application.yml


[官方文档](https://docs.spring.io/spring-boot/docs/2.4.13/reference/html/appendix-application-properties.html)

# application.properties位置作用域

# 项目启动端口号
server.port

# 项目根路径
server.servlet.context-path
## 注意
1. ContextPath must start with '/' and not end with '/'
举例：正确写法 server.servlet.context-path=/sj

# Debug模式
debug=true
开启之后可以查看SpringBoot的启动类有哪些，哪些没启动以及原因是什么
开启后，可以查看理由，如下Redis没生效的原因
```text
   RedisAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.springframework.data.redis.core.RedisOperations' (OnClassCondition)
```