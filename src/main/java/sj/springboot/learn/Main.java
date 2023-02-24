package sj.springboot.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import sj.springboot.learn.bean.Dog;

import java.io.IOException;
import java.util.Arrays;


//SpringBoot主程序注解（启动入口）
@SpringBootApplication
@ServletComponentScan("sj.springboot.learn.servlet")//指定自定义servlet类的包路径，使自定义servlet生效
@MapperScan("sj.springboot.learn.mybatis.dao")
public class Main {
    public static void main(String[] args) throws IOException {
        //启动SpringBoot程序
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
        String[] beanNamesForType = run.getBeanNamesForType(Dog.class);
        Arrays.stream(beanNamesForType).forEach(System.out::println);
        Dog dogC = run.getBean("dogC", Dog.class);
        System.out.println(dogC);
    }
}
