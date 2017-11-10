package com.sogou.bizwork.client.logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jasig.cas.client.validation.Assertion;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 上午11:26:03
 * 类说明
 */
public class DefaultAuthenticationLogger implements AuthenticationLogger {
	
	private static final Logger logger = Logger.getLogger(DefaultAuthenticationLogger.class);
	@Override
	public void authenticationBegin(ServletRequest request,
			ServletResponse response) {
		logger.info("[SSO Client]["+AUTHENTICATION_BEGIN+"]["+((HttpServletRequest)request).getSession().getId()+"]");

	}

	@Override
	public void authenticationSuccess(ServletRequest request,
			ServletResponse response, Assertion assertion) {
		logger.info("[SSO Client]["+AUTHENTICATION_SUCCESS+"]["+((HttpServletRequest)request).getSession().getId()+"]["+assertion.getPrincipal().getName()+"]");

	}

	@Override
	public void authenticationFailed(ServletRequest request,
			ServletResponse response) {
		logger.info("[SSO Client]["+AUTHENTICATION_FAILED+"]["+((HttpServletRequest)request).getSession().getId()+"]");

	}

}
