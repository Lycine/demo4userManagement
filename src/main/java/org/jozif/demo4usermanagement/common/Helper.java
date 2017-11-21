package org.jozif.demo4usermanagement.common;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jozif.demo4usermanagement.common.exception.ServiceException;
import org.jozif.demo4usermanagement.dao.UserRepository;
import org.jozif.demo4usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author hongyu 2017-11-18
 */
@Component
@CommonsLog
public class Helper {

    @Autowired
    private UserRepository userRepository;

    @Value("${demo4userManagement.cookieMaxAgeHour}")
    private String cookieMaxAgeHour;

    public void signInProcedure(HttpServletResponse response, HttpServletRequest request, int uid) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        ServletContext application = request.getServletContext();
        //generate cookie, name from application, value is random
        String ticktName = (String) application.getAttribute(KeyConstant.TICKET_NAME);
        String ticketValue = UUID.randomUUID().toString().substring(0, 10).replace("-", "").toUpperCase();
        Cookie cookie = new Cookie(ticktName, ticketValue);

        //calculate and set cookie expire seconds from application.yml
        Integer cookieMaxAgeSecond = Integer.parseInt(cookieMaxAgeHour) * 60 * 60;
        cookie.setMaxAge(cookieMaxAgeSecond);

        //reload user info from db by uid(key)
        User user = userRepository.findById(uid);
        if (null != user) {
            userRepository.updateLastLoginById(uid);
            cookie.setPath("/");

            //save cookie into application, make a relation
            Map<Integer, String> uidAndCookieMap = (Map<Integer, String>) application.getAttribute(KeyConstant.APPLICATION_UID_AND_COOKIE_MAP);
            uidAndCookieMap.put(user.getId(), ticketValue);

            //save session
            request.getSession().setAttribute(KeyConstant.SESSION_USER, user);

            //send cookie back to user
            response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户

            log.info("user signIn, user: " + user.getId() + ", ip: " + getIpAddr(request));


        } else {
            //when user has been deleted, but user cookie not expired will happen
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + ResultConstant.SIGNIN_USER_NOT_FOUND_INFO);
            throw new ServiceException(ResultConstant.SIGNIN_USER_NOT_FOUND_STATUS, ResultConstant.SIGNIN_USER_NOT_FOUND_INFO);
        }
        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
    }

    public boolean logoutProcedure(HttpServletResponse response, HttpServletRequest request) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        boolean isAlreadyLogout = true;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(KeyConstant.SESSION_USER);
        ServletContext application = request.getServletContext();

        //remove all cookie
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        //remove session
        session.removeAttribute(KeyConstant.SESSION_USER);

        //destory relation
        Map<Integer, String> uidAndCookieMap = (Map<Integer, String>) application.getAttribute(KeyConstant.APPLICATION_UID_AND_COOKIE_MAP);
        if (null != user && uidAndCookieMap.containsKey(user.getId())) {
            isAlreadyLogout = false;
            uidAndCookieMap.remove(user.getId());
            log.info("user logout, user: " + user.getId());
        }
        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
        return isAlreadyLogout;
    }

    public boolean isPasswordVerified(User user, String toVerifyPassword) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                + "param: "
                + "user: " + user.toString() + " "
                + "toVerifyPassword: " + toVerifyPassword);
        boolean isVerified = false;
        try {
            if (StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(toVerifyPassword + user.getSalt()))) {
                isVerified = true;
            }
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + ResultConstant.VERIFY_PASSWORD_UNKNOWN_INFO + " "
                    + e.getMessage(), e);
            throw new ServiceException(ResultConstant.VERIFY_PASSWORD_UNKNOWN_STATUS, ResultConstant.VERIFY_PASSWORD_UNKNOWN_INFO);
        }


        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                + "result: "
                + "isVerified: " + isVerified);
        return isVerified;
    }

    public Map<String, String> generatePasswordAndSalt(String rawPassword) {
//    } else {
//        if (StringUtils.isNotBlank(rawPassword)) {
//            log.error(Thread.currentThread().getName() + " "
//                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
//                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
//                    + ResultConstant.GEN_PASSWORD_SALT_BLANK_RAW_PASSWORD_INFO);
//            throw new ServiceException(ResultConstant.GEN_PASSWORD_SALT_BLANK_RAW_PASSWORD_STATUS, ResultConstant.GEN_PASSWORD_SALT_BLANK_RAW_PASSWORD_INFO);
//        }
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                + "param: "
                + "rawPassword: " + rawPassword);
        try {
            Map<String, String> resultMap = new HashMap<>(16);
            String salt = UUID.randomUUID().toString().replace("-", "");
            String password = DigestUtils.md5Hex(rawPassword + salt);
            resultMap.put("salt", salt);
            resultMap.put("password", password);

            log.info("leave "
                    + Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + "result: "
                    + "password: " + password + " "
                    + "salt: " + salt);
            return resultMap;
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + ResultConstant.GEN_PASSWORD_SALT_BLANK_RAW_PASSWORD_INFO + " "
                    + e.getMessage(), e);
            throw new ServiceException(ResultConstant.GEN_PASSWORD_SALT_UNKNOWN_STATUS, ResultConstant.GEN_PASSWORD_SALT_UNKNOWN_INFO);
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
