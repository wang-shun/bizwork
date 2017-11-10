package com.sogou.bizwork.cas.user.service;

import java.util.List;

import com.sogou.bizwork.cas.exception.BizworkUCException;
import com.sogou.bizwork.cas.user.model.ReceiverDto;
import com.sogou.bizwork.cas.user.model.User;
import com.sogou.bizwork.cas.user.model.UserInfo;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 上午10:58:55
 * 
 */
public interface UserService {

    List<Integer> getEmployeeIdsByGroupId(Integer groupId);

    List<ReceiverDto> getGroupsByIds(List<Integer> groupIds);

    List<ReceiverDto> getUsersByIds(List<Integer> userIds);

    String getNameByEmployeeId(Integer employeeId);

    List<String> getNameByEmployeeIds(List<Integer> employeeId);

    List<Integer> getEmployeeIdsByIds(List<Integer> ids);

    List<Integer> getEmployeeIdsByGroupIds(List<Integer> groupIds);

    User queryUserById(Integer accountId) throws BizworkUCException;

    User queryUserByName(String accountName) throws BizworkUCException;

    void addUser(User user) throws BizworkUCException;

    List<User> queryUsers() throws BizworkUCException;

	User getUserByUsername(String username);


}
