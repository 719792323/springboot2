package sj.springboot.learn.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sj.springboot.learn.lombok.CalResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Import({MyBean.class})
@Configuration(proxyBeanMethods = false)
@ImportResource("classpath:beans.xml")
public class MyConfiguration {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setMethodParam("sj");//进行自定义
        return hiddenHttpMethodFilter;
    }

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    //在有某个Bean的时候这个@Bean才会被实例化到Spring容器中
    @ConditionalOnBean(name = "myBean") //选取名称作为条件，还有很多别的条件可选
    @Bean
    public MyBean2 myBean2() {
        return new MyBean2();
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                HashMap<String, MediaType> mediaTypes = new HashMap<>();
                mediaTypes.put("json", MediaType.APPLICATION_JSON);
                mediaTypes.put("xml", MediaType.APPLICATION_XML);
                //添加自己的
                mediaTypes.put("cal", MediaType.parseMediaType("application/cal"));
                ParameterContentNegotiationStrategy parameterContentNegotiationStrategy
                        = new ParameterContentNegotiationStrategy(mediaTypes);
                //自定义一个叫application-type替换format
                parameterContentNegotiationStrategy.setParameterName("application-type");
                HeaderContentNegotiationStrategy headerContentNegotiationStrategy
                        = new HeaderContentNegotiationStrategy();
                configurer.strategies(Arrays.asList(parameterContentNegotiationStrategy,headerContentNegotiationStrategy));
            }

            //添加参数转换器
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(
                        new Converter<String, CalResult>() {
                            @Override
                            public CalResult convert(String s) {
                                String[] split = s.split("\\*");
                                return new CalResult(
                                        Integer.parseInt(split[0]),
                                        Integer.parseInt(split[1]),
                                        Integer.parseInt(split[0]) * Integer.parseInt(split[1])
                                );
                            }
                        }
                );
            }

            //添加返回值转换器
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new CustomConverter());
            }
        };
    }
}

class CustomConverter implements HttpMessageConverter<CalResult> {

    //该处理器能不能支持参数解析处理（这个不确定是不是）
    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    //就是该处理器能不能进行返回值处理
    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        //这里设置如果是CalResult就支持
        return aClass.isAssignableFrom(CalResult.class);
    }

    //设置该converter能支持的协议类型
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        //设置一个自定义协议类型
        return MediaType.parseMediaTypes(Collections.singletonList("application/cal"));
    }

    //读方法怎么做
    @Override
    public CalResult read(Class<? extends CalResult> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    //写方法怎么做
    @Override
    public void write(CalResult calResult, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        //设置返回一个toString字符串
        String s = calResult.toString();
        //写出
        httpOutputMessage.getBody().write(s.getBytes());
    }
}
