package com.sogou.bizwork.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.task.api.achievement.service.AchievementTService;
import com.sogou.bizwork.task.api.exception.ApiTException;
import com.sogou.bizwork.task.api.medal.bo.MedalInfo;
import com.sogou.bizwork.task.api.score.bo.ScoreInfo;


@Controller
@RequestMapping("/achievement")
public class AchievementController {
	
	@Autowired
	private AchievementTService.Iface achievementTService;

    @RequestMapping(value = "/getScoreInfo.do")
    @ResponseBody
    public Result getScoreInfo(HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        try {
			ScoreInfo scoreInfo = achievementTService.getScoreInfo(user.getEmployeeid());
			
			result.setData(scoreInfo);
		} catch (ApiTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }
    
    
    @RequestMapping(value = "/getMedalInfo.do")
    @ResponseBody
    public Result getMedalInfo(HttpServletRequest request) {
    	Result result = new Result();
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        try {
			List<MedalInfo> medalInfos = achievementTService.getMedalInfo(user.getEmployeeid());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("medalList", medalInfos);
			result.setData(medalInfos);
		} catch (ApiTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
}
