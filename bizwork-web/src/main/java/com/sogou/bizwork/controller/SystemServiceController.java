package com.sogou.bizwork.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.SidSystemItem;
import com.sogou.bizwork.bo.SystemItem;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.service.SystemService;

/**
 * 首页controller
 * @author dongzeguang
 *
 */
@Controller
@RequestMapping("/systemservice")
public class SystemServiceController {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private SystemService systemService;

    /**
     * 获取系统服务分类
     */
    @RequestMapping(value = "/getSystemCategory.do")
    @ResponseBody
    public Result getSystemCategory(HttpServletRequest request) {
        Result result = new Result();
        result.setData(systemService.getSystemCategory());
        return result;
    }

    /**
     * 获取系统服务
     * @param username
     * @return
     */
    @RequestMapping(value = "/getSystemServices.do")
    @ResponseBody
    public Result getSystemServices(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        result.setData(systemService.getSystemServices(user.getEmail()));
        return result;
    }

    /**
     * 新增自定义系统服务
     */
    @RequestMapping(value = "/addUserServices.do")
    @ResponseBody
    public Result addUserServices(HttpServletRequest request, @RequestBody SidSystemItem sidSystemItem) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        SystemItem systemItem = sidSystemItem.getSystemItem();
        if (systemItem.getName().isEmpty() || systemItem.getCategoryId() == null || systemItem.getUrl().isEmpty()) {
            result.setErrorMsg("invalid new item!");
            return result;
        }
        systemItem.setUserEmail(user.getEmail());
        systemItem.setIsNew(1);
        systemService.addUserServices(systemItem);
        return result;
    }

    /**
     * 删除自定义系统服务
     */
    @RequestMapping(value = "/deleteUserServices.do")
    @ResponseBody
    public Result deleteUserServices(HttpServletRequest request, @RequestBody SidSystemItem sidSystemItem) {
        Result result = new Result();
        SystemItem systemItem = sidSystemItem.getSystemItem();
        if (systemItem.getType() == null || systemItem.getId() == null) {
            result.setErrorMsg("id or type can not be null!");
            return result;
        }
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        systemItem.setUserEmail(user.getEmail());
        systemService.deleteUserServices(systemItem);
        return result;
    }
}
