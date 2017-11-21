package org.jozif.demo4usermanagement.service.impl;

import lombok.extern.apachecommons.CommonsLog;
import org.jozif.demo4usermanagement.common.Helper;
import org.jozif.demo4usermanagement.common.KeyConstant;
import org.jozif.demo4usermanagement.common.ResultConstant;
import org.jozif.demo4usermanagement.common.exception.ServiceException;
import org.jozif.demo4usermanagement.dao.UserRepository;
import org.jozif.demo4usermanagement.entity.User;
import org.jozif.demo4usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hongyu 2017-11-21
 */
@CommonsLog
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Helper helper;

    @Override
    public int signIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (null != user) {
            if (KeyConstant.USER_STATUS_NORMAL == user.getStatus()) {
                if (helper.isPasswordVerified(user, password)) {
                    return user.getId();
                }
            } else {
                log.error(Thread.currentThread().getName() + " "
                        + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                        + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                        + ResultConstant.SIGNIN_USER_STATUS_ERROR_INFO);
                throw new ServiceException(ResultConstant.SIGNIN_USER_STATUS_ERROR_STATUS, ResultConstant.SIGNIN_USER_STATUS_ERROR_INFO + ", user status: " + user.getStatus());
            }
        } else {
            log.error(Thread.currentThread().getName() + " "
                    + Thread.currentThread().getStackTrace()[1].getClassName() + " "
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + " "
                    + ResultConstant.SIGNIN_USER_NOT_FOUND_INFO);
            throw new ServiceException(ResultConstant.SIGNIN_USER_NOT_FOUND_STATUS, ResultConstant.SIGNIN_USER_NOT_FOUND_INFO);
        }
        return 0;
    }
}
