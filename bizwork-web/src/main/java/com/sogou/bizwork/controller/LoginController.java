package com.sogou.bizwork.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.SidUser;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.cas.authentication.LdapService;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.constant.ErrorEnum;
import com.sogou.bizwork.service.UserService;
import com.sogou.bizwork.service.modules.ProjectService;

@Controller
public class LoginController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private LdapService ldapService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    // @Autowired
    // private StarryService starryTClient;

    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Result login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Result result = new Result();
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            result.setError(ErrorEnum.PARAM_ERROR);
            return result;
        }

        logger.info("login, username:[" + username + "], password:[" + password + "]");

        username = this.formatUsername(username);
        String email = this.getEmail(username);
        String realPassword = this.getRealPassword(password);
        Integer employeeId = user.getEmployeeid();

        // ldap验证用户名密码
        if (!this.ldapAuthenticate(email, realPassword)) {
            logger.info("ldap authenticate fail, username:[" + username + "], password:[" + password + "]");
            result.setError(ErrorEnum.USERNAME_PASSWORD_ERROR);
            return result;
        }

        // UserEntity.Builder ub = new UserEntity.Builder();
        // ub.setUserName(username);
        // starryTClient.getUser(ub.build()).toString();
        // 登录操作，更新登录时间等信息

        user.setEmail(email);
        user.setPassword(realPassword);
        user.setEmployeeid(employeeId);
        HttpSession session = request.getSession();
        session.setAttribute(Constant.SESSION_USER_KEY, user);

        logger.info("login success, username:[" + username + "]");
        return result;
    }

    private String formatUsername(String username) {
        username = username.toLowerCase().trim();
        if (username.indexOf("@") > 0) {
            return username.substring(0, username.indexOf("@"));
        }
        return username;
    }

    private String getEmail(String username) {
        if (username.indexOf("@") > 0) {
            return username.substring(0, username.indexOf("@")) + "@sogou-inc.com";
        }
        return username + "@sogou-inc.com";
    }

    private String getRealPassword(String password) throws Exception {
        return password;
        // return DesUtil.decode(password);
    }

    private boolean ldapAuthenticate(String username, String password) {
        return this.ldapService.authenticate(username, password);
    }

    @RequestMapping(value = "/logout.do")
    @ResponseBody
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
        if (user != null) {
            session.invalidate();
        }
        return result;
    }

    @RequestMapping(value = "/ifLogin.do")
    @ResponseBody
    public Result ifLogin(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
        if (user == null) {
            session.invalidate();
            result.setError(ErrorEnum.LOGIN_EXPIRE_ERROR);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/createSession.do")
    @ResponseBody
    public Result createSession(@RequestBody SidUser sidUser, HttpServletRequest request)
            throws UnsupportedEncodingException {
        Result result = new Result();
        sidUser.getUserInfo().setUsername(URLDecoder.decode(sidUser.getUserInfo().getUsername(), "utf-8"));
        HttpSession session = request.getSession();
        session.setAttribute(Constant.SESSION_USER_KEY, sidUser.getUserInfo());
        // logger.info("login success, username:[" + userName + "]");
        List<Integer> list = projectService.isLeader(sidUser.getUserInfo());
        boolean bool = false;
        if (list != null && list.size() > 0) {
            bool = true;
        }
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("isLeader", bool);
        result.setData(map);
        return result;
    }

    @RequestMapping(value = "/test.do")
    @ResponseBody
    public Result test() {
        Result result = new Result();
        // UserEntity.Builder ub = new UserEntity.Builder();
        // long systemTime = System.currentTimeMillis();
        // String nameRd = "zhangyu";
        // String emailRd = "zhangyu@sogou-inc.com";
        // ub.setEmail(emailRd);
        // ub.setUserName(nameRd);
        // // starryTClient.getUser(ub.build());
        return result;
    }
}
