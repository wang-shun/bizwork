package com.sogou.bizwork.dao.modules;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sogou.bizwork.bo.modules.GroupSituation;
import com.sogou.bizwork.bo.modules.PersonalSituation;
import com.sogou.bizwork.bo.modules.PersonalTotallineSituation;

public interface UucMapper {

    List<GroupSituation> getGroupSituation(@Param("groupid") Integer groupid,
            @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr);

    List<PersonalSituation> getPersonalSituation(@Param("employee_id") Integer employee_id,
            @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr);

    List<PersonalTotallineSituation> getPersonalTotallineSituationByGroupId(@Param("groupid") Integer groupid,
            @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr);

    List<PersonalTotallineSituation> getPersonalTotallineSituationByUserId(@Param("employee_id") Integer employee_id,
            @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr);

    void saveToGroupProjectTotalline(GroupSituation g);

    void saveToPersonalProjectTotalline(PersonalSituation p);

    void saveUserDateToPersonaltable(PersonalTotallineSituation p);

}
