package com.sogou.bizwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.user.UserQuery;
import com.sogou.bizwork.bo.user.UserQueryInfo;
import com.sogou.bizwork.bo.user.UserQueryResult;
import com.sogou.bizwork.cas.user.model.PersonalInfo;
import com.sogou.bizwork.cas.user.model.PersonalInfoDto;
import com.sogou.bizwork.cas.user.model.UserInfo;
import com.sogou.bizwork.cas.user.model.UserInfoDto;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.service.UserService;
import com.sogou.bizwork.service.modules.ProjectService;
import com.sogou.bizwork.user.po.UserAndGroupPo;
import com.sogou.bizwork.util.Page;
import com.sogou.bizwork.util.StringUtil;


@Controller
@RequestMapping("/user")
public class UserController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/getAll.do")
	@ResponseBody
	public Result getAllUsers() {
		Result result = new Result();
		List<UserAndGroupPo> userPo = userService.getAllUsersAndGroups();
		result.setData(userPo);
		return result;
	}
	
	@RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public Result getUserList(HttpServletRequest request, @RequestBody UserQuery userQuery) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "用户已失效");
            return result;
        }
        UserQueryResult qResult = new UserQueryResult();
        List<UserQueryInfo> userList = userService.getUserListByQuery(userQuery);
        if(CollectionUtils.isNotEmpty(userList)){
        	Page<UserQueryInfo> page = new Page<UserQueryInfo>();
     		page.setPageSize(userQuery.getPageSize());
     		page.setRecords(userList);	
     		List<UserQueryInfo>  pageList = page.getPage(userQuery.getPageNo());
     		qResult.setTotalNumber(userList.size());
     		qResult.setList(pageList);
        }
        result.setData(qResult);
        return result;
	}
	
	@RequestMapping(value = "/getGroupList.do")
    @ResponseBody
    public Result getGroupList(HttpServletRequest request){
		Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "用户已失效");
            return result;
        }
        List<UserAndGroupPo> groups = userService.getAllUsersAndGroups();
        List<UserAndGroupPo> filtedGroups = new ArrayList<UserAndGroupPo>();
        for(UserAndGroupPo g : groups ){
        	if( g.getType() == 1){
        		filtedGroups.add(g);
        	}
        }
        result.setData(filtedGroups);
        return result;
	}
	
    @RequestMapping(value = "/updateUserInfo.do")
    @ResponseBody
    public Result updateUserInfo(@RequestBody UserInfoDto userInfoDto, HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        UserInfo userInfo = userInfoDto.getUserInfo();
        if (userInfo == null) {
        	return result;
        }
    	userInfo.setBirthday(StringUtil.strictTrim(userInfo.getBirthday()));
    	userInfo.setLevel(StringUtil.strictTrim(userInfo.getLevel()));
    	userInfo.setMobile(StringUtil.strictTrim(userInfo.getMobile()));
    	userInfo.setTelephone(StringUtil.strictTrim(userInfo.getTelephone()));
    	userInfo.setIpAddress(StringUtil.strictTrim(userInfo.getIpAddress()));
    	
        if (userInfo.getBirthday() == null &&
        		  userInfo.getLevel() == null &&
				  userInfo.getMobile() == null &&
				  userInfo.getTelephone() == null &&
				  userInfo.getIpAddress() == null &&
				  userInfo.getLeaderId() == 0) {
        	result.setError(421, "所有参数必须完整！");
        	return result;
        }
        String regex = "(?<=(\\b|\\D))(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";
        Pattern pattern = Pattern.compile(regex);  
        if (StringUtils.isNotBlank(userInfo.getIpAddress())) {
	        Matcher matcher = pattern.matcher(userInfo.getIpAddress());
	        if (!matcher.matches()) {
	        	result.setError(431, "你需要输入合法的ipv4地址!");
	        	return result;
	        }
        }
        userService.updateUserInfo(userInfo, user.getEmployeeid());
        return result;
    }
	
    @RequestMapping(value = "/getUserInfo.do")
    @ResponseBody
    public Result getUserInfo(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "employee cannot be null");
            return result;
        }
        List<Integer> list = projectService.isLeader(user);
        boolean isLeader = false;
        if (list != null && list.size() > 0) {
        	isLeader = true;
        }
        logger.info("是不是leader登录:" + isLeader);
        result.setData(userService.getUserInfo(user.getEmployeeid(), isLeader));
        return result;
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping("/getPersonalInfo.do")
    @ResponseBody
    public Result getPersonalInfo(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        
        PersonalInfo personalInfo = userService.getPersonalInfoByEmployeeId(user.getEmployeeid());
        result.setData(personalInfo);
        return result;
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @RequestMapping("/updatePersonalInfo.do")
    @ResponseBody
    public Result updatePersonalInfo(@RequestBody PersonalInfo personalInfo, HttpServletRequest request) {
        Result result = new Result();
        String regex = "(?<=(\\b|\\D))(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(personalInfo.getIpAddress());
        boolean ipIsTrue = false;
        while(matcher.find()){  
            ipIsTrue = true;  
        }  
        if (!ipIsTrue) {
        	result.setError(421, "请输入正确的ipv4地址");
        }
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(personalInfo.getMobile());
        if (!m.matches()) {
        	result.setError(421, "请输入正确的手机号码！");
        }
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        personalInfo.setEmployeeid(user.getEmployeeid());
        userService.updatePersonalInfoByEmployeeId(personalInfo);
        return result;
    }
}