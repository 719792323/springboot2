package sj.springboot.learn.lombok;

import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Data //使用@Data注解会帮助JavaBean生成get、set方法
@ToString //使用@ToString会帮助生成toString方法
@AllArgsConstructor//生成一个使用所有参数的构造器
@NoArgsConstructor//生成一个无参数构造器
@Slf4j //自动注入log4j属性
@EqualsAndHashCode//生产equals方法和HashCode方法
public class LombokBean {
    private String name;
    private String msg;

    public static void main(String[] args) {
       log.info("hello");
    }
}
