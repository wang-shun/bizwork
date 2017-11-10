package com.sogou.bizwork.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.AllWidget;
import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.SidUserWidgets;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.service.HomepageService;

/**
 * 首页controller
 * @author dongzeguang
 *
 */
@Controller
@RequestMapping("/homepage")
public class HomepageController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private HomepageService homepageService;

    /**
     * 得到首页小部件列表
     * @param username
     * @return
     */
    @RequestMapping(value = "/getWidgets.do")
    @ResponseBody
    public Result getSubscribeWidgets(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        result.setData("test");
//        result.setData(homepageService.createAndGetWidgets(user.getEmail()));
        return result;
    }

    /**
     * 获取所有订阅模块
     * @return
     */
    @RequestMapping(value = "/getAllWidgets.do")
    @ResponseBody
    public Result getAllWidgets(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        AllWidget allWidget = new AllWidget();
        allWidget.setSubscribe(homepageService.createAndGetWidgets(user.getEmail()));
        allWidget.setUnsubscribe(homepageService.getNoSubscribeWidgets(user.getEmail()));
        result.setData(allWidget);
        return result;
    }

    /**
     * 发布订阅模块
     * @param sidUserWidgets
     * @return
     */
    @RequestMapping(value = "/releaseWidgets.do")
    @ResponseBody
    public Result releaseWidgets(HttpServletRequest request, @RequestBody SidUserWidgets sidUserWidgets) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        homepageService.releaseWidgets(sidUserWidgets.getUserWidgets(), user.getEmail());
        return result;
    }

}
