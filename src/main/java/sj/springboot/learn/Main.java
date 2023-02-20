package sj.springboot.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import sj.springboot.learn.bean.Person;
import sj.springboot.learn.properties.Address;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//SpringBoot主程序注解（启动入口）
@SpringBootApplication
@ServletComponentScan("sj.springboot.learn.servlet")//指定自定义servlet类的包路径，使自定义servlet生效
public class Main {
    public static void main(String[] args) {
        //启动SpringBoot程序
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
    }
}
