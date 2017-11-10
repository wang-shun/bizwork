package com.sogou.bizwork.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.user.po.UserAndGroupPo;

/**
 * User管理dao
 * @author dongzeguang
 *
 */
@Repository
public interface UserDao {

    public User getUserByName(@Param("username") String username);

    public void addUser(User user);

    public void updateLoginInfo(@Param("username") String username);

    public void updateLogoutInfo(@Param("username") String username);

}
