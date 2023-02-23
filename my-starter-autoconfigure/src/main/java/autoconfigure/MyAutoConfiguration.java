package autoconfigure;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stream.MyStream;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Configuration
@ConditionalOnMissingBean(BufferedInputStream.class)
@EnableConfigurationProperties(MySteamProperties.class)
public class MyAutoConfiguration {

    @Resource
    private MySteamProperties steamProperties;

    @Bean
    public MyStream myStream() throws FileNotFoundException {
        return new MyStream(new BufferedInputStream(new FileInputStream(steamProperties.getFile())));
    }


}
