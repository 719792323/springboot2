import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

/**
https://github.com/719792323/springboot2.git
针对SpringBoot的测试类，2.2版本之前和之后是不一样的。 
* 在2.2版本之前需要添加注解 @SpringBootTest 和 @RunWith(SpringRunner.class) ，在Spring容器环境下进行测试，因为 @Test 导包的是org.junit.Test。 
* 在2.2版本之后只需要添加注解 @SpringBootTest，其中@Test导包为org.junit.jupiter.api.Test。
**/
/**
如果是2.2版本以前则加如下注解
@RunWith(SpringRunner.class
@SpringBootTest(classes = springboot项目驱动类.class)
**/
@SpringBootTest
public class MyTest {
    @Resource
    JdbcTemplate jdbcTemplate;

    @Test
    public void testRedisTemplate() {

    }
}
