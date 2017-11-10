package com.sogou.bizwork.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.SidUser;
import com.sogou.bizwork.cas.exception.BizworkUCException;
import com.sogou.bizwork.cas.user.model.User;
import com.sogou.bizwork.cas.user.service.UserService;

/**
* 员工信息更新controller
*
*/

@Controller
public class StaffController {

    // @Autowired
    // private StarryTService starryTService;
    @Autowired
    private UserService userService;

    /**
     * 新增用户信息
     * @throws BizworkUCException 
     */
    @RequestMapping(value = "/addUser.do")
    @ResponseBody
    public void addUser(@RequestBody SidUser sidUser) throws BizworkUCException {
        User user = convertUserToCasUser(sidUser.getUserInfo());
        // System.out.println(user.getEmail());
        userService.addUser(user);
    }

    User convertUserToCasUser(com.sogou.bizwork.bo.User userPre) {
        User user = new User();
        user.setUserName(userPre.getUsername());
        user.setChineseName(userPre.getChinesename());
        user.setEmail(userPre.getEmail());
        user.setGroupName(userPre.getGroup());
        user.setEmployeeId(userPre.getEmployeeid());
        user.setState(true);
        user.setRole(true);
        user.setJob(userPre.getJob());
        return user;
    }

    // /**
    // * 更新用户信息
    // */
    //
    // @RequestMapping(value = "/updateUserInfo.do")
    // @ResponseBody
    // public Result updateUserInfos(@RequestBody SidUser sidUser) {
    // Result result = new Result();
    // User user = sidUser.getUserInfo();
    // System.out.println(user.getUsername());
    // UserEntity userEntity = convertUserToEntity(user);
    // UserResult userResult = starryTService.updateUser(userEntity);
    // if (!userResult.isStatus()) {
    // result.setError(1, "更新失败");
    // }
    // return result;
    // }

    /**
    * 查询用户信息
     * @throws BizworkUCException 
    */

    @RequestMapping(value = "/queryUserInfo.do")
    @ResponseBody
    public Result queryUserInfo(HttpServletRequest request) throws BizworkUCException {
        Result result = new Result();
        List<User> listUser = new ArrayList<User>();
        listUser = userService.queryUsers();
        result.setData(listUser);
        request.setAttribute("user", listUser);
        return result;
    }

    /**
    * 更新组信息
    */

    // @RequestMapping(value = "/updateGroupInfos.do")
    // @ResponseBody
    // public Result updateGroupInfos(@RequestBody SidStarrayGroup
    // sidStarrayGroup) {
    // Result result = new Result();
    // GroupEntity group = sidStarrayGroup.getGroupInfo();
    // System.out.println(group.getGroupName());
    //
    // starryTService.updateGroup(group);
    //
    // return result;
    // }

    /*
     * User转为UserEntity类型 User 用于操作数据库 UserEntity用于接口交互
     */
    // public static UserEntity convertUserToEntity(User user) {
    // if (user != null) {
    // UserEntity.Builder userBuilder = new UserEntity.Builder();
    // if (user.getId() != null)
    // userBuilder.setId(user.getId());
    // userBuilder.setUserName(user.getUsername());
    // userBuilder.setEmail(user.getEmail());
    // userBuilder.setGroupName(user.getGroup());
    // userBuilder.setTelephone(user.getPhone());
    // userBuilder.setLevel(user.getLevel());
    // return userBuilder.build();
    // }
    // return (new UserEntity.Builder()).build();
    // }
    //
    // public static User convertEntityToUser(UserEntity userEntity) {
    // if (userEntity != null) {
    // User user = new User();
    // user.setId(userEntity.getId());
    // user.setUsername(userEntity.getUserName());
    // user.setEmail(userEntity.getEmail());
    // user.setGroup(userEntity.getGroupName());
    // user.setPhone(userEntity.getTelephone());
    // return user;
    // }
    // return new User();
    // }
}
