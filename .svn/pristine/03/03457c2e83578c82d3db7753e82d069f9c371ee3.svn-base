package com.sogou.bizwork.service;

import java.util.List;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.bo.user.UserQuery;
import com.sogou.bizwork.bo.user.UserQueryInfo;
import com.sogou.bizwork.bo.user.UserStatisticInfo;
import com.sogou.bizwork.cas.user.model.PersonalInfo;
import com.sogou.bizwork.cas.user.model.UserInfo;
import com.sogou.bizwork.user.po.UserAndGroupPo;

/**
 * 用户管理
 * @author dongzeguang
 *
 */
public interface UserService {
	
    public User getUserByName(String username);

    public User login(String username);

    public void logout(String username);

	public List<UserAndGroupPo> getAllUsersAndGroups();

	public void updateUserInfo(UserInfo userInfo, Integer employeeId);

	public UserInfo getUserInfo(Integer employeeId, boolean isLeader);
	
	public List<UserQueryInfo> getUserListByQuery( UserQuery query );
	
	public List<UserStatisticInfo> getHCDescription();

	public PersonalInfo getPersonalInfoByEmployeeId(Integer employeeid);

	public void updatePersonalInfoByEmployeeId(PersonalInfo personalInfo);
	
	
}
