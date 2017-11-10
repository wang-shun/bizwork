package com.sogou.bizwork.dao.modules;

import java.util.List;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.user.GroupInfo;
import com.sogou.bizwork.bo.user.UserQuery;
import com.sogou.bizwork.bo.user.UserQueryInfo;
import com.sogou.bizwork.bo.user.UserStatisticInfo;
import com.sogou.bizwork.user.po.UserAndGroupPo;

public interface UserGroupMapper {

    User selectByPrimaryKey(Integer id);

    List<User> selectByGroupId(Integer groupId);

    List<Integer> isLeader(String leader);

    List<UserAndGroupPo> getAllUsersAndGroups();

    User selectByEmployeeId(Integer employeeid);
    
    List<UserQueryInfo> getUserListByQuery( UserQuery query);
    
    List<UserStatisticInfo> getHCDescription();
    
    List<Integer> getSubGroupIds( String groupAuth);
    
    GroupInfo getGroupInfoById(Integer groupId);
    
    Integer getGroupIdFromLeaderName( String leaderName);
}
