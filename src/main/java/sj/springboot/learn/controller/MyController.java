package sj.springboot.learn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }


    @RequestMapping(value = {"/index", "/"})
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }
}
