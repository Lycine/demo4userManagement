package org.jozif.demo4signinauthentication.common;

import org.jozif.demo4signinauthentication.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;


/**
 * @author hongyu 2017-11-18
 */
@Component
public class Helper {


    @Value("${demo4signInAuthentication.cookieMaxAgeHour}")
    private String cookieMaxAgeHour;

    public void loginProcedure(HttpServletResponse response, HttpServletRequest request) {
        ServletContext application = request.getServletContext();
        //generate cookie, name from application, value is random
        String ticktName = (String) application.getAttribute(KeyConstant.TICKET_NAME);
        String ticketValue = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        Cookie cookie = new Cookie(ticktName, ticketValue);

        //calculate and set cookie expire seconds from application.yml
        Integer cookieMaxAgeSecond = Integer.parseInt(cookieMaxAgeHour) * 60 * 60;
        cookie.setMaxAge(cookieMaxAgeSecond);

        //reload user info from db by uid(key)
        //...
        User user = new User();
        user.setUsername(KeyConstant.CORRECT_USERNAME);
        user.setEmail(KeyConstant.CORRECT_EMAIL);
        user.setId(Integer.parseInt(KeyConstant.CORRECT_USERID));

        //save cookie into application, make a relation
        Map<Integer, String> uidAndCookieMap = (Map<Integer, String>) application.getAttribute(KeyConstant.APPLICATION_UID_AND_COOKIE_MAP);
        uidAndCookieMap.put(Integer.parseInt(KeyConstant.CORRECT_USERID), ticketValue);

        //save session
        request.getSession().setAttribute(KeyConstant.SESSION_USER, user);

        //send cookie back to user
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户
    }
}
