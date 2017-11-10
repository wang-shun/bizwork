package com.sogou.bizwork.cas.authorization;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.handler.AuthenticationException;

import com.sogou.bizwork.cas.user.model.User;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 上午10:17:32
 * 登陆过后的授权接口
 * 
 */
public class BizworkAccountAuthorization {
	private static final Logger logger = Logger.getLogger(BizworkAccountAuthorization.class);
	
	public boolean isAuthorized(User user) throws AuthenticationException {
		
	     logger.debug("Checking authority of Account User \'"
					+ user.getId() + "/" + user.getEmail() + "\'");
		boolean result = true;
		logger.info("Result of authority of Account User \'"
					+ user.getId() + "/" + user.getEmail()
					+ "\' is " + result);
		return result;
	}
}
