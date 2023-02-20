package sj.springboot.learn.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "not found")
public class My404Exception extends RuntimeException {


}
