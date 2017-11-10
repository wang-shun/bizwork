package com.sogou.bizwork.cas.user.service;

import java.util.Map;

import com.sogou.bizwork.cas.user.model.User;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午06:41:34
 * 
 */
public interface UserManageService {

    public static String LAST_LOGIN_TIME = "d_LOGIN_DATE";

    public static String LOGIN_USER_ID = "update_Id";

    public static String LOGIN_EMAIL = "email";

    public static String LOGIN_EMPLOYEE_ID = "employee_id";

    public static String ACCOUNT_LOGIN_ID = "I_ACCOUNT_ID";

    public static String AGENT_LOGIN_USERID = "user_id";

    public static String AGENT_LOGIN_TIME = "last_login";

    public static String UPDATE_TIMESTAMP = "update_timestamp";

    public User isExsitUserAndObtainPrincipal(String username);

    public User isExsitUserById(Long id);

    public Map<String, Object> getUserInfo(String Object);

    public User getPrincipalByUserId(Long id);

    public void updateLoginTime(Long userId);

    public User getUserByusername(String username);

    public String getUsernameByXiaoP(String token);

}
