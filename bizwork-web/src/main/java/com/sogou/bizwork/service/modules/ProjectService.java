package com.sogou.bizwork.service.modules;

import java.util.List;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.modules.BugProject;
import com.sogou.bizwork.bo.modules.ColVal;
import com.sogou.bizwork.bo.modules.Condition;
import com.sogou.bizwork.bo.modules.Project;

public interface ProjectService {

    // 通过数据库查询
    // Integer getGroupIdByUser(User user);
    public User getUsersByEmployeeId(Integer employeeid);

    // 通过数据库查询
    List<User> getUsersByGroupId(List<Integer> groupId);

    List<Integer> isLeader(User user);

    List<Project> getProject(Condition condition);

    List<ColVal> getProjectResult(List<Project> list);

    List<ColVal> getPeopleResult(Condition condition);

    List<ColVal> getGroupProjectTotalline(Integer groupId, String startDateStr, String endDateStr);

    List<ColVal> getPersonalProjectTotalline(Integer id, String startDateStr, String endDateStr);

    void saveToGroupProjectTotalline(String projectName, Integer totalLine, Integer groupId, String startDateStr,
            String endDateStr);

    void saveToPersonalProjectTotalline(String projectName, Integer totalLine, Integer id, String startDateStr,
            String endDateStr);

    List<ColVal> getUsernameTotalline(Integer groupId, String startDateStr, String endDateStr);

    List<ColVal> getUsernameTotalline(Integer groupId, Integer id, String startDateStr, String endDateStr);

    void saveUserDateToPersonaltable(String chinesename, Integer groupId, Integer id, Integer totalLine,
            String startDateStr, String endDateStr);

    String getMonthDate();

    String getQuarterDate();

    public List<BugProject> getProjectBug(Condition condition);

    public List<ColVal> getProjectBugResult(List<BugProject> list);

    public List<ColVal> getPeopleBugResult(Condition condition);

}
