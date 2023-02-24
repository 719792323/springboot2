package sj.springboot.learn.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogConfiguration {

    @Bean//默认情况名称为方法名dogA
    public Dog dogA(){
        return new Dog("a");
    }

    @Bean(name = "dogC")//指定名称位dogC
    public Dog dogB(){
        return new Dog("b");
    }
}
