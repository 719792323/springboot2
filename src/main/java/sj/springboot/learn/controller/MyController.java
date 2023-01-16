package sj.springboot.learn.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sj.springboot.learn.bean.CalResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

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

    @RequestMapping(value = "/rest", method = RequestMethod.GET)
    @ResponseBody
    public String getRest() {
        return "get";
    }

    @RequestMapping(value = "/rest", method = RequestMethod.POST)
    @ResponseBody
    public String postRest() {
        return "post";
    }

    @RequestMapping(value = "/rest", method = RequestMethod.DELETE)
    @ResponseBody
    public String getDelete() {
        return "delete";
    }

    @RequestMapping(value = "/rest", method = RequestMethod.PUT)
    @ResponseBody
    public String putRest() {
        return "put";
    }

    @RequestMapping("/body")
    @ResponseBody
    public String body(@RequestBody String content) {
        System.out.println(content);
        return content;
    }

    @GetMapping(value = "/people")
    @ResponseBody
    public People people(People people) {
        return people;
    }

    @GetMapping(value = "/cal")
    @ResponseBody
    public CalResult cal(@RequestParam("arg") CalResult calResult, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getHeader("accept"));
        if (request.getHeader("accept").equals("application/cal")) {
            response.addHeader("content-type", "application/cal");
        }
        return calResult;
    }

}
