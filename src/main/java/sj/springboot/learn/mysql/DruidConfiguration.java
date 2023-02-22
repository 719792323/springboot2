package sj.springboot.learn.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class DruidConfiguration {

    @Bean
    //将properties中配置好的属性绑定到DataSource中，如下将以下内容注入到DataSource中
    /*
    datasource:
    url: jdbc:mysql://192.168.0.44:3306/dbCloud?useUnicode=true&characterEncoding=utf-8&useSSL=true
    password: 123456
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}
