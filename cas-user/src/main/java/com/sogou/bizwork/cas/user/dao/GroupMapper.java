package com.sogou.bizwork.cas.user.dao;

import com.sogou.bizwork.cas.user.model.Group;
import com.sogou.bizwork.cas.user.model.GroupExample;
import com.sogou.bizwork.cas.user.model.ReceiverDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GroupMapper {
    List<Integer> getEmployeeIdsByGroupId(Integer groupId);
    List<ReceiverDto> getGroupsByIds(List<Integer> groupIds);
    List<ReceiverDto> getUsersByIds(List<Integer> userIds);
    List<Integer> getEmployeeIdsByIds(List<Integer> ids);
    List<Integer> getEmployeeIdsByGroupIds(List<Integer> groupIds);
    
    long countByExample(GroupExample example);

    int deleteByExample(GroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    int insertSelective(Group record);

    List<Group> selectByExample(GroupExample example);

    Group selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Group record, @Param("example") GroupExample example);

    int updateByExample(@Param("record") Group record, @Param("example") GroupExample example);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}