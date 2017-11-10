package com.sogou.bizwork.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.user.GroupInfo;
import com.sogou.bizwork.bo.user.UserQuery;
import com.sogou.bizwork.bo.user.UserQueryInfo;
import com.sogou.bizwork.bo.user.UserStatisticInfo;
import com.sogou.bizwork.cas.constants.UserJobType;
import com.sogou.bizwork.cas.user.dao.UserMapper;
import com.sogou.bizwork.cas.user.model.PersonalInfo;
import com.sogou.bizwork.cas.user.model.UserInfo;
import com.sogou.bizwork.constant.UserStatusEnum;
import com.sogou.bizwork.dao.UserDao;
import com.sogou.bizwork.dao.modules.UserGroupMapper;
import com.sogou.bizwork.service.UserService;
import com.sogou.bizwork.user.po.UserAndGroupPo;

/**
 * 用户服务管理
 * @author dongzeguang
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private UserMapper userMapper;
    
    private final Logger logger = Logger.getLogger(this.getClass());
    @Override
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public User login(String username) {
        User user = userDao.getUserByName(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setStatus(UserStatusEnum.ONLINE.getId());
            userDao.addUser(user);
        } else {
            userDao.updateLoginInfo(username);
        }
        return userDao.getUserByName(username);
    }

    @Override
    public void logout(String username) {
        userDao.updateLogoutInfo(username);
    }

	@Override
	public List<UserAndGroupPo> getAllUsersAndGroups() {
		return userGroupMapper.getAllUsersAndGroups();
	}
	
	@Override
	public List<UserQueryInfo> getUserListByQuery(UserQuery query) {
		if(null != query.getGroupId() && query.getGroupId() != 0){
			GroupInfo parentGroup = userGroupMapper.getGroupInfoById(query.getGroupId());
			if(null != parentGroup){
				query.setGroupIdList(userGroupMapper.getSubGroupIds(parentGroup.getGroupAuth() ));
			}
		}
		if(CollectionUtils.isEmpty(query.getGroupIdList())){
			query.setGroupIdList(null);
		}
		if(StringUtils.isNotEmpty(query.getLeaderName())){
			Integer groupId = userGroupMapper.getGroupIdFromLeaderName(query.getLeaderName());
			GroupInfo parentGroup = userGroupMapper.getGroupInfoById(groupId);
			if(null != parentGroup){
				query.setLeaderGroupIdList(userGroupMapper.getSubGroupIds(parentGroup.getGroupAuth() ));
			}
		}
		return userGroupMapper.getUserListByQuery(query);
	}
	
	@Override
	public void updateUserInfo(UserInfo userInfo, Integer employeeId) {
		userMapper.updateUserInfo(userInfo, employeeId);
	}

	@Override
	public UserInfo getUserInfo(Integer employeeId, boolean isLeader) {
		UserInfo userInfo = userMapper.getUserInfo(employeeId);
		String chineseName = userInfo.getUserName();
		if (StringUtils.isNotBlank(chineseName)) {
			userInfo.setUserLastName(chineseName.substring(chineseName.length() - 1));
		}
		userInfo.setIsLeader(isLeader);
		userInfo.setTitle(UserJobType.parse(Integer.valueOf(userInfo.getTitle())).getText());
		logger.info("是不是leader登录 in service:" + userInfo.getIsLeader());
		return userInfo;
	}

	@Override
	public List<UserStatisticInfo> getHCDescription() {
		return userGroupMapper.getHCDescription();
	}

	@Override
	public PersonalInfo getPersonalInfoByEmployeeId(Integer employeeid) {
		PersonalInfo personalInfo = userMapper.getPersonalInfoByEmployeeId(employeeid);
		if (StringUtils.isNotBlank(personalInfo.getBirthday()) &&
			StringUtils.isNotBlank(personalInfo.getIpAddress()) &&
			StringUtils.isNotBlank(personalInfo.getLevel()) &&
			StringUtils.isNotBlank(personalInfo.getMobile()) &&
			StringUtils.isNotBlank(personalInfo.getTelephone()) 
			) {
			personalInfo.setNeedUpdateInfo(false);
		}
		return personalInfo;
	}

	@Override
	public void updatePersonalInfoByEmployeeId(PersonalInfo personalInfo) {
		userMapper.updatePersonalInfoByEmployeeId(personalInfo);
	}

	
}
