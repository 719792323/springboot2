package sj.springboot.learn.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import sj.springboot.learn.properties.Address;

@Import({MyBean.class})
@Configuration(proxyBeanMethods = false)
@ImportResource("classpath:beans.xml")
public class MyConfiguration {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("sj");//进行自定义
        return hiddenHttpMethodFilter;
    }

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    //在有某个Bean的时候这个@Bean才会被实例化到Spring容器中
    @ConditionalOnBean(name = "myBean") //选取名称作为条件，还有很多别的条件可选
    @Bean
    public MyBean2 myBean2() {
        return new MyBean2();
    }
}

