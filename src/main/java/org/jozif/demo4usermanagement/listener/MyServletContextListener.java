package org.jozif.demo4usermanagement.listener;

import lombok.extern.apachecommons.CommonsLog;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hongyu 2017-11-18
 */
@CommonsLog
@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        ServletContext application = servletContextEvent.getServletContext();

        String ticketName = UUID.randomUUID().toString().substring(0, 10).replace("-", "").toUpperCase();
        application.setAttribute("ticketName", ticketName);
        log.info("ticketName: " + ticketName + " initialized");

        Map<Integer, String> uidAndCookieMap = new HashMap<>(16);
        application.setAttribute("uidAndCookieMap", uidAndCookieMap);
        log.info("uidAndCookieMap initialized");

        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
