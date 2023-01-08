# 自定义欢迎页
自定义欢迎页的默认方式一共有两种
1. 静态资源路径下index.html
注：可以配置静态资源路径，但是不可以配置静态资源的访问前缀。否则导致 index.html不能被默认访问。
即只要任何一个设置的静态资源文件夹有index.html即可，但是不能设置访问前缀。
```yaml
    spring:
    #  mvc:
    #    static-path-pattern: /res/**   这个会导致welcome page功能失效
    resources:
        static-locations: [classpath:/static_html/]
```
2. 写一个controller，该controller的路由是/index
如果设置了资源前缀，那么自能写一个controller
```text
        @RequestMapping(value = {"/index", "/"})
        public ModelAndView index() {
            //隐射了资源路径
            return new ModelAndView("res/index.html");
        }
```

# 自定义Icon
favicon.ico 放在静态资源目录下即可。
注意：设置了静态资源前缀也会导致失效。
如果要设置前缀的解决放是
1. 可以在前端设置这个icon的路径。