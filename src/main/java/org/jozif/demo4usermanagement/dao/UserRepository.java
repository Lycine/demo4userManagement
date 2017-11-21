package org.jozif.demo4usermanagement.dao;

import org.jozif.demo4usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author hongyu 2017-11-20
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * find user by email
     * @param email
     * @return user
     */
    @Query
    User findByEmail(@Param("email") String email);

    /**
     * find user by id
     * @param id
     * @return user
     */
    @Query
    User findById(@Param("id") int id);

    /**
     * update last login by id
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update `user` set GMT_LAST_LOGIN = now() where id = ?", nativeQuery = true)
    int updateLastLoginById(@Param("id") int id);
}
