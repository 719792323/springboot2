# web服务器选择与实例化原理（onConditional注解来确定导入了哪个web服务器）
1. SpringBoot通过onCondition等注解，发现当前应用是web应用。
2. web应用会创建一个web版的ioc容器 **ServletWebServerApplicationContext**
默认情况下SpringBoot使用Tomcat作为web服务器，但是也可以支持如jetty或者undertow等。
确定启动服务器是通过ServletWebServerApplicationContext 容器启动寻找**ServletWebServerFactory并**引导创建服务器
```text 
  ServletWebServerApplicationContext.java
   
   //创建web服务
   private void createWebServer() {
        WebServer webServer = this.webServer;
        ServletContext servletContext = this.getServletContext();
        if (webServer == null && servletContext == null) {
            //获得web工厂
            ServletWebServerFactory factory = this.getWebServerFactory();
            this.webServer = factory.getWebServer(new ServletContextInitializer[]{this.getSelfInitializer()});
            this.getBeanFactory().registerSingleton("webServerGracefulShutdown", new WebServerGracefulShutdownLifecycle(this.webServer));
            this.getBeanFactory().registerSingleton("webServerStartStop", new WebServerStartStopLifecycle(this, this.webServer));
        } else if (servletContext != null) {
            try {
                this.getSelfInitializer().onStartup(servletContext);
            } catch (ServletException var4) {
                throw new ApplicationContextException("Cannot initialize servlet context", var4);
            }
        }

        this.initPropertySources();
    }
    
  //获得web工厂
    protected ServletWebServerFactory getWebServerFactory() {
        String[] beanNames = this.getBeanFactory().getBeanNamesForType(ServletWebServerFactory.class);
        if (beanNames.length == 0) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean.");
        } else if (beanNames.length > 1) {
            throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to multiple ServletWebServerFactory beans : " + StringUtils.arrayToCommaDelimitedString(beanNames));
        } else {
            return (ServletWebServerFactory)this.getBeanFactory().getBean(beanNames[0], ServletWebServerFactory.class);
        }
    }
```
SpringBoot底层默认有很多的WebServer工厂； TomcatServletWebServerFactory, JettyServletWebServerFactory, 
UndertowServletWebServerFactory
```text
TomcatServletWebServerFactory中实例化webServer的方法
本质上是对tomcat实例进行配置

public WebServer getWebServer(ServletContextInitializer... initializers) {
        if (this.disableMBeanRegistry) {
            Registry.disableRegistry();
        }
        //创建tomcat服务
        Tomcat tomcat = new Tomcat();
        //下面就是进行配置的代码
        File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector(this.protocol);
        connector.setThrowOnFailure(true);
        tomcat.getService().addConnector(connector);
        this.customizeConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
        this.configureEngine(tomcat.getEngine());
        Iterator var5 = this.additionalTomcatConnectors.iterator();

        while(var5.hasNext()) {
            Connector additionalConnector = (Connector)var5.next();
            tomcat.getService().addConnector(additionalConnector);
        }

        this.prepareContext(tomcat.getHost(), initializers);
        return this.getTomcatWebServer(tomcat);
    }

```
4. 底层直接会有一个自动配置类。**ServletWebServerFactoryAutoConfiguration**,ServletWebServerFactoryAutoConfiguration
导入了ServletWebServerFactoryConfiguration（配置类）
```text
注意下面的：@Import中的内容

@Configuration(
    proxyBeanMethods = false
)
@AutoConfigureOrder(Integer.MIN_VALUE)
@ConditionalOnClass({ServletRequest.class})
@ConditionalOnWebApplication(
    type = Type.SERVLET
)
@EnableConfigurationProperties({ServerProperties.class})
@Import({BeanPostProcessorsRegistrar.class, ServletWebServerFactoryConfiguration.EmbeddedTomcat.class, ServletWebServerFactoryConfiguration.EmbeddedJetty.class, ServletWebServerFactoryConfiguration.EmbeddedUndertow.class})
public class ServletWebServerFactoryAutoConfiguration {

}
```
```text
 ServletWebServerFactoryConfiguration.EmbeddedTomcat.class
 @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({Servlet.class, Tomcat.class, UpgradeProtocol.class})
    @ConditionalOnMissingBean(
        value = {ServletWebServerFactory.class},
        search = SearchStrategy.CURRENT
    )
    static class EmbeddedTomcat {
        EmbeddedTomcat() {
        }

        @Bean
        TomcatServletWebServerFactory tomcatServletWebServerFactory(ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers, ObjectProvider<TomcatContextCustomizer> contextCustomizers, ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
            TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
            factory.getTomcatConnectorCustomizers().addAll((Collection)connectorCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getTomcatContextCustomizers().addAll((Collection)contextCustomizers.orderedStream().collect(Collectors.toList()));
            factory.getTomcatProtocolHandlerCustomizers().addAll((Collection)protocolHandlerCustomizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }
    }
```
6. ServletWebServerFactoryConfiguration 配置类 根据动态判断系统中到底导入了那个Web服务器的包。
（**默认是web-starter导入tomcat包**），容器中就有 TomcatServletWebServerFactory
7. TomcatServletWebServerFactory 创建出Tomcat服务器并启动；TomcatWebServer 的构造器拥有初始化方法initialize->this.tomcat.start();
```text
public class TomcatWebServer implements WebServer {
     private void initialize() throws WebServerException {
         ...
         this.tomcat.start();
         ...
     }
}
```
内嵌服务器，就是手动把启动服务器的代码调用（tomcat核心jar包存在）

# 切换web服务器方法
因为spring-boot-starter-web导入了spring-boot-starter-tomcat
```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```
spring-boot-starter-web
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
      <version>2.3.4.RELEASE</version>
      <scope>compile</scope>
    </dependency>
```
所以默认情况下用的就是tomcat，根据原理可知只要去除tomcat的依赖，导入需要的服务器依赖就可以完成服务器切换
```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```

# 定制Web服务器
1. 可以通过修改properties或者yaml的方式
都是修改其server开头的属性，属性分为通用属性和特有属性，
* 通用属性比如：
server.port=xx
* 专有属性，假如对于undertow来说
server.undertow.accesslog.dir=xx
```text
@ConfigurationProperties(
    prefix = "server",
    ignoreUnknownFields = true
)
public class ServerProperties {
    private final Tomcat tomcat;
    private final Jetty jetty;
    private final Netty netty;
    private final Undertow undertow;
}
```
2. 自定义Factory工厂 WebServerFactoryCustomizer
```text
@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(9000);
    }

}
```