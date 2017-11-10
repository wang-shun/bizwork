package com.sogou.bizwork.controller.modules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.modules.BugProject;
import com.sogou.bizwork.bo.modules.ColVal;
import com.sogou.bizwork.bo.modules.Condition;
import com.sogou.bizwork.bo.modules.Project;
import com.sogou.bizwork.bo.modules.ReturnData;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.service.modules.ProjectService;

/**
 * 获取整个项目组的代码或者个人的完成情况controller
 * @author lijiantong
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ProjectService projectService;

    @Value("${date.month}")
    private String month;

    @Value("${date.quarter}")
    private String quarter;

    @Value("${query.project}")
    private String query_project;

    @Value("${query.user}")
    private String query_user;

    @Value("${jobType_ceshi}")
    private String jobType_ceshi;

    @RequestMapping(value = "/getProjects.do", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Result getProject(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        String dateType = params.get("dateType").toString();
        String queryType = params.get("queryType").toString();
        // 下面是根据调用接口传入的参数来得到startDateStr endDateStr
        if ("".equals(dateType) || null == dateType) {
            result.setErrorMsg("dateType参数为空");
            return result;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String endDateStr = df.format(new Date());
        String startDateStr = null;
        if (month.equals(dateType)) {
            startDateStr = projectService.getMonthDate();
        } else if (quarter.equals(dateType)) {
            startDateStr = projectService.getQuarterDate();
        } else {
            result.setErrorMsg("dateType参数错误");
            return result;
        }

        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);

        // 从数据库中查询user信息封装成我要的对象，防止之前的user没有我想要的字段。
        user = projectService.getUsersByEmployeeId(user.getEmployeeid());

        List<Integer> groupIds = projectService.isLeader(user);
        boolean isLeader = false;
        if (groupIds != null && groupIds.size() > 0) {
            isLeader = true;
        }
        logger.info("是不是leader登录:" + isLeader);
        String job = user.getJob();
        if (job == null) {
            user.setJob("1");
            job = "1";
        }
        int groupSize = 0;
        List<User> users = new ArrayList<User>();
        if (isLeader) {
            // 通过组ID得到该项目组所有的人
            users = projectService.getUsersByGroupId(groupIds);
            groupSize = users.size();
        } else {
            user = projectService.getUsersByEmployeeId(user.getEmployeeid());
            users.add(user);
        }

        // 下面根据queryType来获取是根据项目还是根据人员来来获取数据
        Condition condition = new Condition(users, startDateStr, endDateStr);
        if (null == queryType || "".equals(queryType)) {
            result.setErrorMsg("queryType参数为空");
            return result;
        }

        if (query_project.equals(queryType)) {

            if (jobType_ceshi.equals(job)) {
                // 新增代码，检测到当前用户是测试人员，则进行这段逻辑。
                // 先查数据库，没有就通过网络获得，并将结果存入数据库
                ReturnData res = new ReturnData();
                List<String> colname = new ArrayList<String>();// 抬头
                colname.add(Constant.PROJECTNAME);
                colname.add(Constant.TOTALBUG);
                res.setColname(colname);
                List<ColVal> colval = new ArrayList<ColVal>();
                if (isLeader) {
                    // 如果是leader，得到整个项目组的项目完成情况
                    colval = projectService.getGroupProjectTotalline(user.getGroupid(), startDateStr, endDateStr);
                } else {
                    // 得到自己的项目完成情况
                    colval = projectService.getPersonalProjectTotalline(user.getEmployeeid(), startDateStr, endDateStr);
                }
                if (colval.size() == 0) {
                    logger.info("数据库中没有数据，从网络获取");
                    List<BugProject> list = projectService.getProjectBug(condition);// 得到这个人或者整个项目组完成项目的情况
                    colval = projectService.getProjectBugResult(list);
                    if (isLeader) {
                        for (ColVal c : colval) {
                            String projectName = c.getKey();
                            Integer totalBug = (int) c.getValue();
                            projectService.saveToGroupProjectTotalline(projectName, totalBug, user.getGroupid(),
                                    startDateStr, endDateStr);
                        }
                    } else {
                        for (ColVal c : colval) {
                            String projectName = c.getKey();
                            Integer totalBug = (int) c.getValue();
                            projectService.saveToPersonalProjectTotalline(projectName, totalBug, user.getEmployeeid(),
                                    startDateStr, endDateStr);
                        }
                    }
                }
                // 获得总计 和totalbug
                int Total = 0;
                for (ColVal c : colval) {
                    Total += c.getValue();
                }
                ColVal c = new ColVal(Constant.TOTAL, Total);
                colval.add(0, c);
                res.setColval(colval);
                result.setData(res);
                return result;
            }

            // 这是获取开发人员数据，先查数据库，没有就通过网络获得，并将结果存入数据库
            ReturnData res = new ReturnData();
            List<String> colname = new ArrayList<String>();// 抬头
            colname.add(Constant.PROJECTNAME);
            colname.add(Constant.TOTALLINE);
            res.setColname(colname);
            List<ColVal> colval = new ArrayList<ColVal>();
            if (isLeader) {
                // 如果是leader，得到整个项目组的项目完成情况
                colval = projectService.getGroupProjectTotalline(user.getGroupid(), startDateStr, endDateStr);

            } else {
                // 得到自己的项目完成情况
                colval = projectService.getPersonalProjectTotalline(user.getEmployeeid(), startDateStr, endDateStr);
            }
            if (colval.size() == 0) {
                logger.info("数据库中没有数据，从网络获取");
                List<Project> list = projectService.getProject(condition);// 得到这个人或者整个项目组完成项目的情况
                colval = projectService.getProjectResult(list);
                if (isLeader) {
                    for (ColVal c : colval) {
                        String projectName = c.getKey();
                        Integer totalLine = (int) c.getValue();
                        projectService.saveToGroupProjectTotalline(projectName, totalLine, user.getGroupid(),
                                startDateStr, endDateStr);
                    }
                } else {
                    for (ColVal c : colval) {
                        String projectName = c.getKey();
                        Integer totalLine = (int) c.getValue();
                        projectService.saveToPersonalProjectTotalline(projectName, totalLine, user.getEmployeeid(),
                                startDateStr, endDateStr);
                    }
                }
            }
            // 获得总计 和totalline
            int Total = 0;
            for (ColVal c : colval) {
                Total += c.getValue();
            }
            ColVal c = new ColVal(Constant.TOTAL, Total);
            colval.add(0, c);
            res.setColval(colval);
            result.setData(res);
        } else if (query_user.equals(queryType)) {

            if (jobType_ceshi.equals(job)) {
                ReturnData res = new ReturnData();
                List<String> colname = new ArrayList<String>();// 抬头
                colname.add(Constant.USERNAME);
                colname.add(Constant.TOTALBUG);
                res.setColname(colname);
                List<ColVal> colval = new ArrayList<ColVal>();
                if (isLeader) {
                    colval = projectService.getUsernameTotalline(user.getGroupid(), startDateStr, endDateStr);
                } else {
                    colval = projectService.getUsernameTotalline(user.getGroupid(), user.getEmployeeid(), startDateStr,
                            endDateStr);
                }
                // 不加(isLeader && res.size() < groupSize)条件会出现
                // leader查看所有人的情况时，只显示了数据库中存在的人数,而不是显示该组所有人的情况
                if (colval == null || colval.size() == 0 || (isLeader && colval.size() < groupSize)) {
                    logger.info("数据库中没有数据，从网络获取");
                    colval = projectService.getPeopleBugResult(condition);
                    for (ColVal c : colval) {
                        String chinesename = c.getKey();
                        Integer employee_id = 0;
                        if (CollectionUtils.isEmpty(users)) {
                            result.setErrorMsg("用户信息未登录或用户信息未成功获取");
                            return result;
                        }
                        for (User u : users) {
                            if (null != u.getChinesename() && u.getChinesename().equals(chinesename)) {
                                employee_id = u.getEmployeeid();
                                break;
                            }
                        }
                        Integer totalLine = (int) c.getValue();
                        projectService.saveUserDateToPersonaltable(chinesename, user.getGroupid(), employee_id,
                                totalLine, startDateStr, endDateStr);
                    }
                }
                // 获得总计 和totalline
                int Total = 0;
                for (ColVal c : colval) {
                    Total += c.getValue();
                }
                ColVal c = new ColVal(Constant.TOTAL, Total);
                colval.add(0, c);
                res.setColval(colval);
                result.setData(res);
                return result;
            }

            ReturnData res = new ReturnData();
            List<String> colname = new ArrayList<String>();// 抬头
            colname.add(Constant.USERNAME);
            colname.add(Constant.TOTALLINE);
            res.setColname(colname);
            List<ColVal> colval = new ArrayList<ColVal>();
            if (isLeader) {
                colval = projectService.getUsernameTotalline(user.getGroupid(), startDateStr, endDateStr);
            } else {
                colval = projectService.getUsernameTotalline(user.getGroupid(), user.getEmployeeid(), startDateStr,
                        endDateStr);
            }
            // 不加(isLeader && res.size() < groupSize)条件会出现
            // leader查看所有人的情况时，只显示了数据库中存在的人数,而不是显示该组所有人的情况
            if (colval == null || colval.size() == 0 || (isLeader && colval.size() < groupSize)) {
                logger.info("数据库中没有数据，从网络获取");
                colval = projectService.getPeopleResult(condition);
                for (ColVal c : colval) {
                    String chinesename = c.getKey();
                    Integer employee_id = 0;
                    if (CollectionUtils.isEmpty(users)) {
                        result.setErrorMsg("用户信息未登录或用户信息未成功获取");
                        return result;
                    }
                    for (User u : users) {
                        if (null != u.getChinesename() && u.getChinesename().equals(chinesename)) {
                            employee_id = u.getEmployeeid();
                            break;
                        }
                    }
                    Integer totalLine = (int) c.getValue();
                    projectService.saveUserDateToPersonaltable(chinesename, user.getGroupid(), employee_id, totalLine,
                            startDateStr, endDateStr);
                }
            }
            // 获得总计 和totalline
            int Total = 0;
            for (ColVal c : colval) {
                Total += c.getValue();
            }
            ColVal c = new ColVal(Constant.TOTAL, Total);
            colval.add(0, c);
            res.setColval(colval);
            result.setData(res);
        } else {
            result.setErrorMsg("queryType参数错误");
            return result;
        }
        return result;
    }
}
