package com.sogou.bizwork.cas.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.sogou.bizwork.cas.exception.BizworkUCException;
import com.sogou.bizwork.cas.user.dao.GroupMapper;
import com.sogou.bizwork.cas.user.dao.UserMapper;
import com.sogou.bizwork.cas.user.model.ReceiverDto;
import com.sogou.bizwork.cas.user.model.User;
import com.sogou.bizwork.cas.user.model.UserExample;
import com.sogou.bizwork.cas.user.service.UserService;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 上午11:10:36
 * 
 */
// @Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    GroupMapper groupMapper;

    @Override
    public User queryUserById(Integer accountId) throws BizworkUCException {
        return userMapper.selectByPrimaryKey(accountId);
    }

    @Override
    public User queryUserByName(String accountName) throws BizworkUCException {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(accountName);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public GroupMapper getGroupMapper() {
        return groupMapper;
    }

    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public void addUser(User user) throws BizworkUCException {
        userMapper.insert(user);

    }

    @Override
    public List<User> queryUsers() throws BizworkUCException {
        return userMapper.selectUsers();
    }

    @Override
    public List<Integer> getEmployeeIdsByGroupId(Integer groupId) {
        return groupMapper.getEmployeeIdsByGroupId(groupId);
    }

    @Override
    public List<ReceiverDto> getGroupsByIds(List<Integer> groupIds) {
        // TODO Auto-generated method stub
        return groupMapper.getGroupsByIds(groupIds);
    }

    @Override
    public List<ReceiverDto> getUsersByIds(List<Integer> userIds) {
        // TODO Auto-generated method stub
        return groupMapper.getUsersByIds(userIds);
    }

    @Override
    public String getNameByEmployeeId(Integer employeeId) {
        // TODO Auto-generated method stub
        return userMapper.getNameByEmployeeId(employeeId);
    }

    @Override
    public List<String> getNameByEmployeeIds(List<Integer> employeeIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employeeIds", employeeIds);
        return userMapper.getNameByEmployeeIds(map);
    }

    @Override
    public List<Integer> getEmployeeIdsByIds(List<Integer> ids) {
        // TODO Auto-generated method stub
        return groupMapper.getEmployeeIdsByIds(ids);
    }

    @Override
    public List<Integer> getEmployeeIdsByGroupIds(List<Integer> groupIds) {
        // TODO Auto-generated method stub
        return groupMapper.getEmployeeIdsByGroupIds(groupIds);
    }

	@Override
	public User getUserByUsername(String userName) {
		// TODO Auto-generated method stub
		return userMapper.getUserByUsername(userName);
	}


}
