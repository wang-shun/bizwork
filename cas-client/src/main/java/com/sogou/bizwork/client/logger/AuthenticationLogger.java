package com.sogou.bizwork.client.logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jasig.cas.client.validation.Assertion;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 上午11:24:19
 * 类说明
 */
public interface AuthenticationLogger {
	
	public final String AUTHENTICATION_BEGIN = "Authentication Begin";
	
	public final String AUTHENTICATION_SUCCESS = "Authentication Success";
	
	public final String AUTHENTICATION_FAILED = "Authentication Failed";
	
	public void authenticationBegin(ServletRequest request, ServletResponse response);
	
	public void authenticationSuccess(ServletRequest request, ServletResponse response, Assertion assertion);
	
	public void authenticationFailed(ServletRequest request, ServletResponse response);

}
