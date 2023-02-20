package sj.springboot.learn.servlet;

import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Random;

@WebFilter(urlPatterns = {"/servlet/*"}) //注意原生servlet的拦截通配路径是/*不是/**
@Log
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //过滤器初始化方法
        log.info("MyFilter init");
    }

    private Random random = new Random();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("MyFilter doFilter");
        if (random.nextInt() % 2 == 0) {//放行
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //拦截逻辑
            servletResponse.getWriter().write("filter");
        }
    }

    @Override
    public void destroy() {
        //过滤器销毁方法
        log.info("MyFilter destroy");
    }
}
