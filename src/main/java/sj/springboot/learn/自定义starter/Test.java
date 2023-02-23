package sj.springboot.learn.自定义starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//@Configuration
public class Test {

    @Bean
    public BufferedInputStream inputStream() throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream("D:/Code/Java/springboot2/src/main/resources/application.yml"));
    }
}
