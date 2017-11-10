package com.sogou.bizwork.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizdev.starry.api.user.GroupTService;
import com.sogou.bizdev.starry.api.user.StarryTService;
import com.sogou.bizdev.starry.api.user.UserTService;
import com.sogou.bizdev.starry.entity.UserAndGroupEntity;
import com.sogou.bizdev.starry.entity.UserAndGroupResult;
import com.sogou.bizdev.starry.entity.UserEntity;
import com.sogou.bizdev.starry.entity.UserResult;
import com.sogou.bizwork.bo.Result;

@Controller
@RequestMapping("/thrift")
public class ThriftController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private StarryTService starryTService;
    @Autowired
    private UserTService userTService;
    @Autowired
    private GroupTService groupTService;

    @RequestMapping("/getAllUsers.do")
    @ResponseBody
    public Result getAllUsers() {
        Result result = new Result();
        List<String> list = new ArrayList<String>();
        UserResult user = userTService.getAllUser();
        List<UserEntity> entity = user.getUserEntityList();
        for (UserEntity en : entity) {
            list.add(en.getUserName());
        }
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/getList.do")
    @ResponseBody
    public Result getList(HttpServletRequest request) throws TException {
        Result result = new Result();
        String queryType = request.getParameter("type");
        if ((queryType != null && !"".equals(queryType.trim()))
                && (!"group".equals(queryType) && !"person".equals(queryType))) {
            result.setErrorMsg("查询参数错误");
            return result;
        }
        UserAndGroupResult res = starryTService.getList(queryType);
        List<UserAndGroupEntity> list = res.getUserAndGroupEntityList();
        if (list == null || list.size() < 1) {
            result.setErrorMsg("获取小组或者用户失败");
            return result;
        }
        result.setData(list);
        return result;
    }

}