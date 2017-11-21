package org.jozif.demo4usermanagement;

import lombok.extern.apachecommons.CommonsLog;
import org.jozif.demo4usermanagement.common.Helper;
import org.jozif.demo4usermanagement.dao.UserRepository;
import org.jozif.demo4usermanagement.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@CommonsLog
public class Demo4userManagementTests {

    @Autowired
    private Helper helper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {

    }

    @Test
    public void helperGeneratePasswordAndSalt() {
        log.info("start");
        Map<String, String> map = helper.generatePasswordAndSalt("123456");
        for (String key : map.keySet()) {
            log.info("key: " + key + ", value: " + map.get(key));
        }
        log.info("end");
    }

    @Test
    public void helperVerifyPassword() {
        log.info("start");
        User user = new User();
        user.setSalt("b3329d559c7148c29fe3b71c7b16cae5");
        user.setPassword("edaf0c71e363f500564fa67137fa0b31");
        String toVerifyPassword = "123456";
        log.info(helper.isPasswordVerified(user, toVerifyPassword));
        log.info("end");
    }

    @Test
    public void daoFindByEmail() {
        log.info("start");
        User user = userRepository.findByEmail("xx@xx.com");
        log.info("user: " + user.toString());
        log.info("end");
    }

    @Test
    public void daoFindById() {
        log.info("start");
        User user = userRepository.findById(1);
        log.info("user: " + user.toString());
        log.info("end");
    }

    @Test
    public void daoUpdateLastLoginById() {
        log.info("start");
        int i = userRepository.updateLastLoginById(1);
        log.info("i: " + i);
        log.info("end");
    }
}
