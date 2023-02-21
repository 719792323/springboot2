package sj.springboot.learn.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class ValueTest {
    @Value("${value.name}")
    private String valueName;
    @Value("${value.age}")
    private Integer valueAge;

}
