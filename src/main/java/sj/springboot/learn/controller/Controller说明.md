# 1.创建一个普通的Controller类
* 注意:@Controller默认是进行****页面跳转的注解****,该类中的方法如果String,则代表资源的路径,也可以用
ModelAndView.
```java
    //controller注解，表示这是一个controller类
    @Controller
    public class MyController {
       
        @RequestMapping("/hello") //路由注解
        @ResponseBody //以字符串的形式返回
        public String hello() {
            return "hello";
        }
    }
```

# 2.Controller相关注解
## @ResponseBody
注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
* 在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
* 可以写在方法或者类上，写在方法上只作用于该方法，写在类上则作用于整个类所有方法
## @RestController
相当于在一个打了Controller注解的类上加上ResponseBody注解
## @ControllerAdvice
## @RestControllerAdvice

# 3.Rest风格的增删改查URL方式
* 非Rest写法：/getUser   获取用户     /deleteUser 删除用户    /editUser  修改用户       /saveUser 保存用户
* Rest写法： /user    GET-获取用户    DELETE-删除用户     PUT-修改用户      POST-保存用户

修改@RequestMapping中的Method属性，**如果不指定该方法默认接受所有类型请求**
也可以用各种具体的Mapping如  @GetMapping、@DeleteMapping....
如下展示了如何用Rest风格进行增删改查User：
```text
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getUser(){
        return "GET-张三";
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String saveUser(){
        return "POST-张三";
    }


    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public String putUser(){
        return "PUT-张三";
    }

    @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    public String deleteUser(){
        return "DELETE-张三";
    }


	@Bean
	@ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
	@ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled", matchIfMissing = false)
	public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new OrderedHiddenHttpMethodFilter();
	}

```
### 注意
1. 在默认情况下用form发起的请求只能支持GET和POST请求，就算form表单中些了DELELE或者PUT都会认为是GET被GET方法处理
解决方法是必须在form表当中设置一个_method行.
2. 如果原生支持PUT DELETE请求的发送,则不需要做如下面的工作,因为表单不支持PUT DELETE才要做下面的工作.
```html
    <form action="/user" method="post"> 这里必须写method=post,如果是method=get,下面就算写了_method还是会路由到get方法
        <input type="_method" type="hidden" value="DELETE"> 这行是起作用的构建
    </form>
```
还需要在yaml中开启这种映射关系
```yaml
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true
```
实现原理就是实现了一个拦截器,查看这个请求是否是POST方法并且是否有_method参数,
然后根据url路径和_method隐射到对应的方法,所以如果要自定义这个参数,可以自己重新对应的拦截规则
下面是SpringBoot的默认配置实现
```text
    @Bean
    @ConditionalOnMissingBean({HiddenHttpMethodFilter.class})
    @ConditionalOnProperty(
        prefix = "spring.mvc.hiddenmethod.filter",
        name = {"enabled"},
        matchIfMissing = false
    )
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new OrderedHiddenHttpMethodFilter();
    }
```
自定义
```java
@Configuration(proxyBeanMethods = false)
public class MyConfiguration {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("sj");//进行自定义
        return hiddenHttpMethodFilter;
    }
}
```
