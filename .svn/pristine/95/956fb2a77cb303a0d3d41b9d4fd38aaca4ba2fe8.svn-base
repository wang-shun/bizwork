package com.sogou.bizwork.service.modules.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.modules.BugJsonObject;
import com.sogou.bizwork.bo.modules.BugProject;
import com.sogou.bizwork.bo.modules.ColVal;
import com.sogou.bizwork.bo.modules.Condition;
import com.sogou.bizwork.bo.modules.GroupSituation;
import com.sogou.bizwork.bo.modules.PersonalSituation;
import com.sogou.bizwork.bo.modules.PersonalTotallineSituation;
import com.sogou.bizwork.bo.modules.Project;
import com.sogou.bizwork.dao.modules.UserGroupMapper;
import com.sogou.bizwork.dao.modules.UucMapper;
import com.sogou.bizwork.service.modules.ProjectService;

/**
 * 获取整个项目组的代码或者个人的完成情况service
 * @author lijiantong
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private UucMapper uucMapper;

    @Override
    public List<User> getUsersByGroupId(List<Integer> groupIds) {
        // 通过调用dao层从数据库中获取
        List<User> res = new ArrayList<User>();
        for (Integer groupId : groupIds) {
            List<User> list = userGroupMapper.selectByGroupId(groupId);
            res.addAll(list);
        }
        return res;
    }

    @Override
    public User getUsersByEmployeeId(Integer employeeid) {
        User user = userGroupMapper.selectByEmployeeId(employeeid);
        return user;
    }

    @Override
    public List<Integer> isLeader(User user) {
        // 这个需要查询grouptable,因为grouptable中只有leader的中文名和邮箱，这里根据中文名查询
        List<Integer> str = new ArrayList<Integer>();
        str = userGroupMapper.isLeader(user.getEmail());
        return str;
    }

    @Override
    public List<Project> getProject(Condition condition) {
        // 获取原始数据，不处理，处理的过程交给getProjectResult，getPeopleResult
        List<User> users = condition.getUsers();
        String startDateStr = condition.getStartDateStr();
        String endDateStr = condition.getEndDateStr();
        List<Project> res = new ArrayList<Project>();
        for (User u : users) {
            String url = "http://bizqa.sogou-inc.com/ws-mgr/stat/getdevStatByProjectDetail";
            url = url + "?email=" + u.getEmail() + "&startDateStr=" + startDateStr + "&endDateStr=" + endDateStr;
            // 获取服务器给我返回的json数据
            String jsonString = null;
            jsonString = httpGet(u.getEmail(), url);
            // 获取到了原始的数据
            List<Project> list = parseJson(jsonString);
            res.addAll(list);
        }
        return res;
    }

    @Override
    public List<BugProject> getProjectBug(Condition condition) {
        List<User> users = condition.getUsers();
        String startDateStr = condition.getStartDateStr();
        String endDateStr = condition.getEndDateStr();
        List<BugProject> res = new ArrayList<BugProject>();
        for (User u : users) {
            String url = "http://bizqa.sogou-inc.com/ws-mgr/stat/getQaStatbugDetail";
            url = url + "?qaName=" + u.getEmail() + "&startDateStr=" + startDateStr + "&endDateStr=" + endDateStr;
            // 获取服务器给我返回的json数据
            String jsonString = null;
            jsonString = httpGet(u.getEmail(), url);
            // 获取到了原始的数据
            List<BugProject> list = parseJsonBug(jsonString);
            if (null == list || list.size() == 0) {
                return res;
            }
            res.addAll(list);
        }
        return res;
    }

    @Override
    public List<ColVal> getProjectResult(List<Project> list) {
        List<ColVal> res = new ArrayList<ColVal>();
        // List<Project> tmp = new ArrayList<Project>();
        // 这里使用Map效率高一些，但是就不好使用pID去判断项目是否相同，只能使用pName判断，不是特别规范
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Project p : list) {
            String projectName = p.getProjectname();
            Integer totalline = p.getTotalline();
            if (map.containsKey(projectName)) {
                totalline += map.get(projectName);
                map.put(projectName, totalline);
            } else {
                map.put(projectName, totalline);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String projectName = entry.getKey();
            Integer totalline = entry.getValue();
            ColVal c = new ColVal(projectName, totalline);
            res.add(c);
        }
        Collections.sort(res, this.getColDescComparator());
        return res;
    }

    private Comparator<ColVal> getColDescComparator() {
        return new Comparator<ColVal>() {

            @Override
            public int compare(ColVal o1, ColVal o2) {
                return (int) (o2.getValue() - o1.getValue());
            }
        };

    }

    @Override
    public List<ColVal> getProjectBugResult(List<BugProject> list) {
        List<ColVal> res = new ArrayList<ColVal>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (BugProject p : list) {
            String projectName = p.getProjectname();
            if (map.containsKey(projectName)) {
                map.put(projectName, map.get(projectName) + 1);
            } else {
                map.put(projectName, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String projectName = entry.getKey();
            Integer totalline = entry.getValue();
            ColVal c = new ColVal(projectName, totalline);
            res.add(c);
        }
        Collections.sort(res, this.getColDescComparator());
        return res;
    }

    @Override
    public List<ColVal> getPeopleResult(Condition condition) {
        // 这个方法返回Project 中的每个人的情况
        List<ColVal> res = new ArrayList<ColVal>();
        List<User> users = condition.getUsers();
        String startDate = condition.getStartDateStr();
        String endDate = condition.getEndDateStr();
        for (User u : users) {
            String url = "http://bizqa.sogou-inc.com/ws-mgr/stat/getdevStatByProjectDetail";
            url = url + "?email=" + u.getEmail() + "&startDateStr=" + startDate + "&endDateStr=" + endDate;
            // 获取服务器给我返回的json数据
            String jsonString = null;
            jsonString = httpGet(u.getEmail(), url);
            // 获取到了原始的数据
            List<Project> list = new ArrayList<Project>();
            list = parseJson(jsonString);
            Integer total = 0;
            for (Project p : list) {
                total += p.getTotalline();
            }
            ColVal col = new ColVal(u.getChinesename(), total);
            res.add(col);
        }
        Collections.sort(res, this.getColDescComparator());
        return res;
    }

    @Override
    public List<ColVal> getPeopleBugResult(Condition condition) {
        // 这个方法返回Project 中的每个人的情况
        List<ColVal> res = new ArrayList<ColVal>();
        List<User> users = condition.getUsers();
        String startDate = condition.getStartDateStr();
        String endDate = condition.getEndDateStr();
        for (User u : users) {
            String url = "http://bizqa.sogou-inc.com/ws-mgr/stat/getQaStatbugDetail";
            url = url + "?qaName=" + u.getEmail() + "&startDateStr=" + startDate + "&endDateStr=" + endDate;
            // 获取服务器给我返回的json数据
            String jsonString = null;
            jsonString = httpGet(u.getEmail(), url);
            // 获取到了原始的数据
            List<BugProject> list = new ArrayList<BugProject>();
            list = parseJsonBug(jsonString);
            int size = list.size();
            ColVal col = new ColVal(u.getChinesename(), size);
            res.add(col);
        }
        Collections.sort(res, this.getColDescComparator());
        return res;
    }

    @Override
    public List<ColVal> getGroupProjectTotalline(Integer groupId, String startDateStr, String endDateStr) {
        List<ColVal> list = new ArrayList<ColVal>();
        List<GroupSituation> res = new ArrayList<GroupSituation>();
        res = uucMapper.getGroupSituation(groupId, startDateStr, endDateStr);
        for (GroupSituation g : res) {
            String name = g.getProjectname();
            Integer total = g.getTotalline();
            ColVal colval = new ColVal(name, total);
            list.add(colval);
        }
        return list;
    }

    @Override
    public List<ColVal> getPersonalProjectTotalline(Integer employee_id, String startDateStr, String endDateStr) {
        List<ColVal> list = new ArrayList<ColVal>();
        List<PersonalSituation> res = new ArrayList<PersonalSituation>();
        res = uucMapper.getPersonalSituation(employee_id, startDateStr, endDateStr);
        for (PersonalSituation g : res) {
            String name = g.getProjectname();
            Integer total = g.getTotalline();
            ColVal colVal = new ColVal(name, total);
            list.add(colVal);
        }
        return list;
    }

    @Override
    public void saveToGroupProjectTotalline(String projectName, Integer totalLine, Integer groupId,
            String startDateStr, String endDateStr) {
        GroupSituation g = new GroupSituation(groupId, projectName, totalLine, startDateStr, endDateStr);
        uucMapper.saveToGroupProjectTotalline(g);

    }

    @Override
    public void saveToPersonalProjectTotalline(String projectName, Integer totalLine, Integer id, String startDateStr,
            String endDateStr) {
        PersonalSituation p = new PersonalSituation(id, projectName, totalLine, startDateStr, endDateStr);
        uucMapper.saveToPersonalProjectTotalline(p);

    }

    @Override
    public List<ColVal> getUsernameTotalline(Integer groupId, String startDateStr, String endDateStr) {
        if (groupId == null || startDateStr == null || endDateStr == null)
            return new ArrayList<ColVal>();
        List<ColVal> list = new ArrayList<ColVal>();
        List<PersonalTotallineSituation> res = new ArrayList<PersonalTotallineSituation>();
        res = uucMapper.getPersonalTotallineSituationByGroupId(groupId, startDateStr, endDateStr);
        if (res != null) {
            for (PersonalTotallineSituation g : res) {
                String name = g.getChinesename();
                Integer total = g.getTotalline();
                ColVal col = new ColVal(name, total);
                list.add(col);
            }
        }
        return list;
    }

    @Override
    public List<ColVal> getUsernameTotalline(Integer groupId, Integer employee_id, String startDateStr,
            String endDateStr) {
        if (groupId == null || employee_id == null || startDateStr == null || endDateStr == null)
            return new ArrayList<ColVal>();
        List<ColVal> list = new ArrayList<ColVal>();
        List<PersonalTotallineSituation> res = new ArrayList<PersonalTotallineSituation>();
        res = uucMapper.getPersonalTotallineSituationByUserId(employee_id, startDateStr, endDateStr);
        if (res != null) {
            for (PersonalTotallineSituation g : res) {
                String name = g.getChinesename();
                Integer total = g.getTotalline();
                ColVal col = new ColVal(name, total);
                list.add(col);
            }
        }
        return list;
    }

    @Override
    public void saveUserDateToPersonaltable(String chinesename, Integer groupId, Integer id, Integer totalLine,
            String startDateStr, String endDateStr) {
        List<PersonalTotallineSituation> res = new ArrayList<PersonalTotallineSituation>();
        res = uucMapper.getPersonalTotallineSituationByUserId(id, startDateStr, endDateStr);
        if (res.size() == 0) {
            PersonalTotallineSituation p = new PersonalTotallineSituation(groupId, id, chinesename, totalLine,
                    startDateStr, endDateStr);
            uucMapper.saveUserDateToPersonaltable(p);
        }

    }

    private String httpGet(String email, String url) {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
        // 下面的代码使得在cookie层登录进去了
        HttpState hState = httpClient.getState();

        Cookie cookie = new Cookie();
        cookie.setDomain("bizqa.sogou-inc.com");
        cookie.setPath("/");
        cookie.setName("__cookie__wisdom__uname__");
        String usename = email.substring(0, email.indexOf("@"));
        cookie.setValue(usename);
        hState.addCookie(cookie);
        httpClient.setState(hState);

        httpClient.getHostConfiguration().setHost("bizqa.sogou-inc.com", 80, "http");

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
        // String info = null;
        String response = null;
        try {
            // info = new String(getMethod.getResponseBodyAsString());
            // 请求成功，使用 byte数组来获取响应数据
            byte[] responsebody = getMethod.getResponseBody();
            // 编码要和 服务端响应的一致
            response = new String(responsebody, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private List<Project> parseJson(String json) {
        JSONObject jsonObj = JSON.parseObject(json);
        JSONArray result = jsonObj.getJSONArray("data");
        List<Project> list = JSON.parseArray(result.toJSONString(), Project.class);
        return list;
    }

    private List<BugProject> parseJsonBug(String json) {
        List<BugProject> list = new ArrayList<BugProject>();
        JSONObject jSONObject = JSON.parseObject(json).getJSONObject("data");
        if (null == jSONObject) {
            return list;
        }
        BugJsonObject<Map<String, BugProject>> res = (BugJsonObject<Map<String, BugProject>>) JSON.parseObject(json,
                new TypeReference<BugJsonObject<Map<String, BugProject>>>() {});
        if (null == res || res.getData().size() == 0) {
            return list;
        }
        for (BugProject b : res.getData().values()) {
            list.add(b);
        }
        return list;
    }

    @Override
    public String getMonthDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        String startDateStr = df.format(cal.getTime());
        return startDateStr;
    }

    @Override
    public String getQuarterDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        Calendar cal = Calendar.getInstance();
        String startDateStr = null;
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                cal.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                cal.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                cal.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                cal.set(Calendar.MONTH, 9);
            cal.set(Calendar.DATE, 1);
            startDateStr = df.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return startDateStr;
    }

}
