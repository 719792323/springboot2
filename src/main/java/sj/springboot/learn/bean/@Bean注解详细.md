# @Bean在IOC的名称
默认注入到容器当中的对象id（有的也叫做名称）是方法名，
但是如果需要显式命名，则可以在注解上使用 name 属性（或其别名{value}）。
比如：@Bean(name = "名称")或者@Bean("名称")，
当然也可以有多个名称@Bean(name = { "dataSource1", "dataSource2", "dataSource3" })

# 根据名称获取Bean实例对象
```java
@SpringBootApplication
public class SpringBootCacheApplication {
    public static void main(String[] args) {
        // 返回ioc容器
        ConfigurableApplicationContext run =
                SpringApplication.run(SpringBootCacheApplication.class, args);
        //获取ioc容器中的组件名字并打印出来
        String[] userBeanNames = run.getBeanNamesForType(Dog.class);
        for (String s : userBeanNames){
            System.out.println(s);
        }
        // 判断id为user01的对象是否存在
        boolean contains = run.containsBean("dogC");
        System.out.println(contains);
        //获取dogC
        Dog dogC = run.getBean("dogC", Dog.class);
        System.out.println(dogC);
    }
}

```



# 依赖注入
@Bean也可以依赖其他任意数量的bean，
如下示例：创建User 对象的时候**需要使用到Student** ，
而Student 恰好也在容器当中，那我们就不需要在user01方法中创建了，
直接从容器里面获取即可，@Bean可以通过方法参数实现依赖注入，
在执行user01方法的时候，spring会根据参数名称先去容器当中找Student对象，
找不到根据对象的class类型找，有点类似于@Resource注解（先根据名称，再根据类型），
如果找到直接以参数形式传给user01方法，如果没找到，启动项目直接会报could not be found异常！

```java
@Configuration
public class Config {
    // 给容器中加入组件，以方法名作为组件id
    @Bean
    public User user01(Student student) {
        User user = new User();
        user.setName(student.getName());
        return user;
    }
}

```

# 生命周期钩子函数
任何使用@Bean定义的bean，也可以执行生命周期的回调函数，
如下示例：就是向容器当中存入bean的时候执行init方法，然后销毁的时候执行destroy方法。
```java
@Configuration
public class Config {

    // 给容器中加入组件，以方法名作为组件id
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public User user01() {
        User user = new User();
        return user;
    }
}

public class User {
    private Integer id;
    private String name;
    private String phone;
    // get、set省略...
    public void init(){
        System.out.println("User初始化了");
    }

    public void destroy(){
        System.out.println("User销毁了");
    }
}
```
注意：destroy方法在项目停止运行的时候是不会执行的，必须是项目仍然正常运行，从容器当中移除该对象，这时候是会执行destroy方法的。
```java
public class DemoUtil {

    @Autowired
    private ApplicationContext applicationContext;

	// 添加bean
    public void addBean(String beanName, Class<?> beanClass) {
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

	// 从容器当中移除bean
    public void removeBean(String beanName) {
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        beanDefinitionRegistry.removeBeanDefinition(beanName);
    }

}
```

# @Bean注解增强
@Bean注释不提供Profile, Scope, Lazy, DependsOn, Primary, Order功能，
意思是，如果要在bean上配置Profile, Scope, Lazy, DependsOn, Primary, Order这些的时候，
不再是用属性了，而是在该bean上使用它们对应的注解。
```text
@Bean
@Profile("dev")
@Scope("prototype")
@Order(-100)
public MyBean myBean() {
        // instantiate and configure MyBean obj
        return obj;
}
```
@Profile: 指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件
@Scope： 他用来配置Bean实例的作用域对象。@Scope 具有以下几种作用域：
singleton：单实例的(单例)(默认)----全局有且仅有一个实例
prototype：多实例的(多例)---- 每次获取Bean的时候会有一个新的实例
reqeust：同一次请求----request：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
session：同一个会话级别---- session：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
@Lazy： 可以延迟加载bean对象，即在使用时才去初始化。一是可以减少Spring的IOC容器启动时的加载时间，二是可以解决bean的循环依赖问题。
@DependsOn： 主要用于指定当前bean所依赖的beans。任何被指定依赖的bean都由Spring保证在当前bean之前创建。在少数情况下，bean不是通过属性或构造函数参数显式依赖于另一个bean，但却需要要求另一个bean优先完成初始化，则可以使用@DependsOn这个注解。
@Primary： 假如容器当中有两个A对象，使用@Autowired根据类型注入就会异常，@Primary是一种在注入点级别解决歧义的机制，可以解决该问题！
@Order： 主要用来控制配置类的加载顺序，设置的值越小越优先加载！
