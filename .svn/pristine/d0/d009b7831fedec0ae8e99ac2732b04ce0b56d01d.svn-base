package com.sogou.bizwork.controller.modules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.modules.Advice;
import com.sogou.bizwork.bo.modules.SidAdvice;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.service.modules.AdviceService;
import com.sogou.bizwork.service.modules.ProjectService;

/**
 * 有话要说--意见反馈
 * @author lijiantong
 */
@Controller
@RequestMapping("/advice")
public class AdviceController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AdviceService adviceService;
    @Autowired
    private ProjectService projectService;

    // 从前端获取意见对象，存入数据库
    @RequestMapping(value = "/putAdivce.do")
    @ResponseBody
    public Result putAdivce(HttpServletRequest request, @RequestBody SidAdvice sidAdivce) {
        Result result = new Result();
        if (sidAdivce == null || sidAdivce.getAdvice() == null) {
            result.setErrorMsg("Invalid Parameter");
            return result;
        }
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "用户已失效");
            return result;
        }
        user = projectService.getUsersByEmployeeId(user.getEmployeeid());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Advice advice = sidAdivce.getAdvice();
        advice.setTime(date);
        adviceService.saveUserAdvice(advice, user.getChinesename());
        return result;
    }

    // 从数据库取出该用户最新的意见
    @RequestMapping(value = "/getAdivces.do")
    @ResponseBody
    public Result getAdivces(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        if (user.getEmployeeid() == null) {
            result.setError(421, "用户已失效");
            return result;
        }
        user = projectService.getUsersByEmployeeId(user.getEmployeeid());
        List<Advice> advice = new ArrayList<Advice>();
        advice = adviceService.getUserAdvice(user.getChinesename());
        result.setData(advice);
        return result;
    }

    // @RequestMapping(value = "/test.do")
    // @ResponseBody
    // public Result test(HttpServletRequest request) {
    // Result result = new Result();
    // Advice advice = new Advice();
    // advice.setComment("我是一个评论");
    // List<String> picUrl = new ArrayList<String>();
    // picUrl.add("sdhfasufbsdfhbasfk");
    // picUrl.add("rfrrfergbvbnghjhjfhj");
    // picUrl.add("dsdfsdfasdfasdfasdf");
    // picUrl.add("sdhfasufbsdfhbasfk");
    // picUrl.add("sdhfasufbsdfhbasfk");
    // advice.setPicUrl(picUrl);
    // SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // String date = myFmt.format(new Date());
    // advice.setTime(date);
    // adviceService.saveUserAdvice(advice, "李建同");
    // return result;
    // }
}
