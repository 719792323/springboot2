package sj.springboot.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import sj.springboot.learn.mybatis.service.Service;
import sj.springboot.learn.mybatis.service.ServiceImpl;

//SpringBoot主程序注解（启动入口）
@SpringBootApplication
@ServletComponentScan("sj.springboot.learn.servlet")//指定自定义servlet类的包路径，使自定义servlet生效
@MapperScan("sj.springboot.learn.mybatis.dao")
public class Main {
    public static void main(String[] args) {
        //启动SpringBoot程序
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);

        Service service = run.getBean(ServiceImpl.class);
        System.out.println(service.getAllEntities());
    }
}
