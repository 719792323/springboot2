package sj.springboot.learn.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class MyRegistration {

    class Servlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.getWriter().write("MyRegistration myServlet");
        }
    }
    //实现Servlet
    @Bean
    public ServletRegistrationBean myServlet() {
        return new ServletRegistrationBean(new Servlet(), "/myRegistration/servlet");
    }

    //实现Filter
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean
                = new FilterRegistrationBean<>((req, resp, fc) -> {
            resp.getWriter().write("MyRegistration myFilter");
        });
        filterFilterRegistrationBean.setUrlPatterns(Collections.singleton("/myRegistration/filter"));
        return filterFilterRegistrationBean;
    }
    class Listener implements ServletContextListener{
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContextListener.super.contextInitialized(sce);
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            ServletContextListener.super.contextDestroyed(sce);
        }
    }
    //实现Listener
    @Bean
    public ServletListenerRegistrationBean myListener(){

        return new ServletListenerRegistrationBean(new Listener());
    }
}
