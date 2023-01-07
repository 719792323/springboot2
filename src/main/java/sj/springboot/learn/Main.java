package sj.springboot.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//SpringBoot主程序注解（启动入口）
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //启动SpringBoot程序
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);

        for (String beanDefinitionName : run.getBeanDefinitionNames()) {
            if (beanDefinitionName.contains("Bean")) {
                System.out.println(beanDefinitionName);
            }
            /*
            输出两个
            1.sj.springboot.learn.config.MyBean 这个是由Import注解创建
            2.myBean 这个是被@Bean创建
             */
        }
    }
}
