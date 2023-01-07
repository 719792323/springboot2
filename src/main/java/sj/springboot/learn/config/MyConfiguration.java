package sj.springboot.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({MyBean.class})
@Configuration(proxyBeanMethods = false)
public class MyConfiguration {
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
