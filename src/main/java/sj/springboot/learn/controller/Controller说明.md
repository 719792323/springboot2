1. 创建一个普通的Controller类

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

# RestController
相当于在一个打了Controller注解的类上加上ResponseBody注解