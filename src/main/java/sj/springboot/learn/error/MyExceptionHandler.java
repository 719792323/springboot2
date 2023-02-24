package sj.springboot.learn.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sj.springboot.learn.lombok.LombokBean;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {
    //返回值可以是视图地址或者ModelAndView对象
    @ExceptionHandler(My404Exception.class)//可以处理的异常类
    @ResponseBody
    public Object _404Error(Exception e) {
        log.error("_404Error:{}", e);
        return new LombokBean("name","msg");
    }
}

