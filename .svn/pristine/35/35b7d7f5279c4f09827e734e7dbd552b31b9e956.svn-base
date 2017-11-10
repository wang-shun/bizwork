package com.sogou.bizwork.cas.user.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.sogou.bizwork.cas.constants.AuthenticationLogHelper;
import com.sogou.bizwork.cas.constants.SSOConstantsCode;
import com.sogou.bizwork.cas.constants.SSOLogConstantsCode;
import com.sogou.bizwork.cas.constants.ThreadLocalHolder;
import com.sogou.bizwork.cas.constants.UUCConstants;
import com.sogou.bizwork.cas.constants.UserJobType;
import com.sogou.bizwork.cas.exception.BizworkUCException;
import com.sogou.bizwork.cas.user.model.User;
import com.sogou.bizwork.cas.user.service.UserManageService;
import com.sogou.bizwork.cas.user.service.UserService;
import com.sogou.bizwork.cas.utils.JasonUtils;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-12 下午02:12:07
 * 
 */
public class UserManageServiceImpl implements UserManageService, UUCConstants, SSOConstantsCode, SSOLogConstantsCode {

    private static final Logger logger = Logger.getLogger(UserManageServiceImpl.class);
    private static final User NULL_USER = new User();

    @Resource
    private UserService userService;

    @Override
    public User isExsitUserAndObtainPrincipal(String username) {
        User account = (User) ThreadLocalHolder.getProperty(CURRENT_LOGIN_USER);
        /*
         * if (null != account&&account.getId()!=null) { return account ==
         * NULL_USER ? null : account; }
         */

        try {
            account = userService.queryUserByName(username);
            if (null == account) {
                AuthenticationLogHelper.userAuthenticationLog(AUTHENTICATION_FAILED,
                        AUTHENTICATION_FAILED_USER_NOT_EXISTS,
                        (String) ThreadLocalHolder.getProperty(CURRENT_LOGIN_SERVICE),
                        (String) ThreadLocalHolder.getProperty(CURRENT_LOGIN_SID), null, username, null,
                        "User Not Exists " + username);
                ThreadLocalHolder.bindProperty(CURRENT_LOGIN_USER, NULL_USER);
                return null;
            }
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_USER, account);
            return account;
        } catch (final DataAccessException e) {
            AuthenticationLogHelper.userAuthenticationLog(AUTHENTICATION_FAILED,
                    AUTHENTICATION_FAILED_DATA_ACCESS_EXCEPTION,
                    (String) ThreadLocalHolder.getProperty(CURRENT_LOGIN_SERVICE),
                    (String) ThreadLocalHolder.getProperty(CURRENT_LOGIN_SID), null, username, null,
                    "Exception(Incorrect result size) " + e.getMessage() + username);
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_USER, NULL_USER);
            return null;
        } catch (Exception e) {
            AuthenticationLogHelper.userAuthenticationLog(AUTHENTICATION_FAILED,
                    AUTHENTICATION_FAILED_SYSTEM_EXCEPTION,
                    (String) ThreadLocalHolder.getProperty(CURRENT_LOGIN_SERVICE),
                    (String) ThreadLocalHolder.getProperty(CURRENT_LOGIN_SID), null, username, null,
                    "Exception" + e.getMessage() + username);
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_USER, NULL_USER);
            return null;
        }
    }

    @Override
    public User isExsitUserById(Long id) {
        User user = (User) ThreadLocalHolder.getProperty(CURRENT_LOGIN_USER);
        if (null != user) {
            return user == NULL_USER ? null : user;
        }
        try {
            user = userService.queryUserById(id.intValue());
        } catch (BizworkUCException e) {
            if (e.getErrorInfo() != null) {
                logger.warn(" function = queryUserById accountId=" + id + ",errorInfo=" + e.getErrorInfo().toString());
            } else {
                logger.warn(" function = queryUserById accountId=" + id + "," + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public Map<String, Object> getUserInfo(String Object) {
        Map<String, Object> map = new HashMap<String, Object>();
        User account = null;
        account = getUserByusername(Object);
        if (account != null) {
            map.put(USER_EMAIL, account.getEmail());
            map.put(USER_NAME, account.getChineseName());
            map.put(USER_ID, account.getId());
            map.put(USER_GROUP, account.getGroupName());
            map.put(USER_LEVEL, account.getLevel());
            map.put(USER_EMPLOYEE_ID, account.getEmployeeId());
            if (account.getJob() != null) {
                String jobTitle = UserJobType.parse(Integer.valueOf(account.getJob())).getText();
                map.put(USER_TITLE, jobTitle);
            } else {
                map.put(USER_TITLE, "");
            }
            map.put(USER_PHONE, account.getTelephone());
            map.put(USER_PHOTO, account.getPhoto());

        }
        return map;
    }

    @Override
    public User getPrincipalByUserId(Long id) {
        User user = null;
        try {
            if (id != null) {
                user = userService.queryUserById(id.intValue());
            }
        } catch (BizworkUCException e) {
            if (e.getErrorInfo() != null) {
                logger.warn(" function = queryUserById userid=" + id + ",errorInfo=" + e.getErrorInfo().toString());
            } else {
                logger.warn(" function = queryUserById userid=" + id + "," + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public void updateLoginTime(Long userId) {
        // TODO Auto-generated method stub

    }

    @Override
    public User getUserByusername(String username) {
        User user = null;
        try {
            if (username != null) {
                user = userService.queryUserByName(username);
            }
        } catch (BizworkUCException e) {
            if (e.getErrorInfo() != null) {
                logger.warn(" lanting invoke error,use old ,function = getUserByusername username=" + username
                        + ",errorInfo=" + e.getErrorInfo().toString());
            } else {
                logger.warn(" lanting invoke error,use old ,function = getUserByusername username=" + username + ","
                        + e.getMessage());
            }
        }
        return user;

    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getUsernameByXiaoP(String token) {
        // 封装一个http GET或者POST请求小P的接口，得到json数据，然后取得用户名
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
        String url = "https://puboa.sogou-inc.com/moa/sylla/mapi/pns/auth" + "?token=" + token;
        GetMethod getMethod = new GetMethod(url);
        // 响应状态的判断
        int status = 0;
        try {
            status = httpClient.executeMethod(getMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 200 ok 请求成功，否则请求失败
        if (status != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + getMethod.getStatusLine());
        }
        // 请求成功，使用string获取响应数据
        String response = null;
        try {
            // 请求成功，使用 byte数组来获取响应数据
            byte[] responsebody = getMethod.getResponseBody();
            // 编码要和 服务端响应的一致
            response = new String(responsebody, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username = getUserNameFromJson(response);
        return username;

    }

    private String getUserNameFromJson(String response) {
        String username = JasonUtils.getUserNameFromJson(response);
        return username;
    }
}
