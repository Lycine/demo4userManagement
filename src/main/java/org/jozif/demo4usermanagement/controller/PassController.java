package org.jozif.demo4usermanagement.controller;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.jozif.demo4usermanagement.common.Helper;
import org.jozif.demo4usermanagement.common.KeyConstant;
import org.jozif.demo4usermanagement.common.ResultConstant;
import org.jozif.demo4usermanagement.common.exception.ServiceException;
import org.jozif.demo4usermanagement.entity.User;
import org.jozif.demo4usermanagement.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private UserService userService;

    @Autowired
    private Helper helper;

    @Value("${demo4userManagement.cookieMaxAgeHour}")
    private String cookieMaxAgeHour;

    @RequestMapping(value = "/pass/signIn", produces = "application/json")
    @ResponseBody
    public String signIn(HttpServletResponse response, HttpServletRequest request) {
        log.info("enter "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");

        JSONObject result = new JSONObject();
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password)) {
                int uid = userService.signIn(email, password);
                if (0 != uid) {
                    try {
                        helper.logoutProcedure(response, request);
                        helper.signInProcedure(response, request, uid);
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
                    log.warn(ResultConstant.SIGNIN_BAD_USERNAME_PASSWORD_INFO);
                    result.put("status", ResultConstant.SIGNIN_BAD_USERNAME_PASSWORD_STATUS);
                    result.put("info", ResultConstant.SIGNIN_BAD_USERNAME_PASSWORD_INFO);
                }
            } else {
                log.warn(ResultConstant.SIGNIN_INCOMPLETELY_PARAM_INFO);
                result.put("status", ResultConstant.SIGNIN_INCOMPLETELY_PARAM_STATUS);
                result.put("info", ResultConstant.SIGNIN_INCOMPLETELY_PARAM_INFO);
            }
        }catch (ServiceException se) {
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + se.getMessage(), se);
            result.put("status", se.getCode());
            result.put("info", se.getMessage());
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
        result.put("data", "Hello, " + user.getName());

        log.info("leave "
                + Thread.currentThread().getName() + " "
                + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " ");
        return result.toString();
    }
}
