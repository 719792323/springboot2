package sj.springboot.learn.servlet;

import lombok.extern.java.Log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Log
@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("context init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("context destroy");
    }
}
