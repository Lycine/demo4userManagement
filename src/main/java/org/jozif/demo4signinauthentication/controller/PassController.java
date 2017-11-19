package org.jozif.demo4signinauthentication.controller;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.jozif.demo4signinauthentication.common.Helper;
import org.jozif.demo4signinauthentication.common.KeyConstant;
import org.jozif.demo4signinauthentication.common.ResultConstant;
import org.jozif.demo4signinauthentication.entity.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hongyu 2017-11-18
 */
@CommonsLog
@RestController
public class PassController {

    @Autowired
    private Helper helper;

    @Value("${demo4signInAuthentication.cookieMaxAgeHour}")
    private String cookieMaxAgeHour;

    @RequestMapping(value = "/pass/signIn", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String signIn(HttpServletResponse response, HttpServletRequest request) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        JSONObject result = new JSONObject();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (StringUtils.equals(username, KeyConstant.CORRECT_USERNAME) && StringUtils.equals(password, KeyConstant.CORRECT_PASSWORD)) {
                try {
                    helper.logoutProcedure(response, request);
                    helper.signInProcedure(response, request);
                    result.put("status", ResultConstant.SIGNIN_SUCCESS_STATUS);
                    result.put("info", ResultConstant.SIGNIN_SUCCESS_INFO);
                } catch (Exception e) {
                    log.error(Thread.currentThread().getName() + " "
                            + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                            + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                            + e.getMessage(), e);
                    result.put("status", ResultConstant.SIGNIN_FAILURE_STATUS);
                    result.put("info", ResultConstant.SIGNIN_FAILURE_INFO);
                }
            } else {
                result.put("status", ResultConstant.SIGNIN_BAD_USERNAME_PASSWORD_STATUS);
                result.put("info", ResultConstant.SIGNIN_BAD_USERNAME_PASSWORD_INFO);
            }
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + e.getMessage(), e);
            result.put("status", ResultConstant.SIGNIN_UNKNOWN_STATUS);
            result.put("info", ResultConstant.SIGNIN_UNKNOWN_INFO);
        }


        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
        return result.toString();
    }

    @RequestMapping(value = "/pass/logout")
    @ResponseBody
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        JSONObject result = new JSONObject();

        try {
            boolean isAlreadyLogout = helper.logoutProcedure(response, request);
            if (isAlreadyLogout) {
                result.put("status", ResultConstant.LOGOUT_ALREADY_LOGOUT_STATUS);
                result.put("info", ResultConstant.LOGOUT_ALREADY_LOGOUT_INFO);

            } else {
                result.put("status", ResultConstant.LOGOUT_SUCCESS_STATUS);
                result.put("info", ResultConstant.LOGOUT_SUCCESS_INFO);
            }
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + e.getMessage(), e);
            result.put("status", ResultConstant.LOGOUT_UNKNOWN_STATUS);
            result.put("info", ResultConstant.LOGOUT_UNKNOWN_INFO);
        }

        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
        return result.toString();
    }

    @RequestMapping(value = "/afterSignInCanVisit", produces = "application/json")
    @ResponseBody
    public String passedVisit(HttpServletResponse response, HttpServletRequest request) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        JSONObject result = new JSONObject();

        User user = (User) request.getSession().getAttribute(KeyConstant.SESSION_USER);
        result.put("status", ResultConstant.PASSED_VISIT_SUCCESS_STATUS);
        result.put("info", ResultConstant.PASSED_VISIT_SUCCESS_INFO);
        result.put("data", "Hello, " + user.getUsername());

        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
        return result.toString();
    }
}
