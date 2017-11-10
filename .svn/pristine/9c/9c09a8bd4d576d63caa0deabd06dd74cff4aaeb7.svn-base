package com.sogou.bizwork.client.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sogou.bizwork.cas.utils.StringUtils;
import com.sogou.bizwork.client.utils.PathPatternMatcher;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-20 下午02:27:52
 * 类说明
 */
public class ExampleBizworkSingleSignOnFilter extends BizworkSSOFilter{
	
	private static final Logger logger = Logger.getLogger(ExampleBizworkSingleSignOnFilter.class);
	
	public static final String CONFIG_EXCLUDE_PATH = "excludePath";
	private List<String> excludePath;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		String excludePathv = filterConfig.getInitParameter(CONFIG_EXCLUDE_PATH);

		excludePath = new ArrayList<String>();
		if (!StringUtils.isBlank(excludePathv)) {
			String[] paths = excludePathv.split(";");
			excludePath.addAll(Arrays.asList(paths));
		}
 	}
	
	/**
	 * @return whether the SSO/SLO process can be by-passed
	 */
	protected boolean needSSO(ServletRequest request, ServletResponse response) {
		//判断是否登陆过，已登录返回false，否则返回true代表走SSO
		HttpServletRequest hrequest = (HttpServletRequest) request;
    	HttpServletResponse hresponse = (HttpServletResponse) response;
     	String path = hrequest.getServletPath();

		if("".equals(path) || PathPatternMatcher.urlPathMatch(excludePath, path)) {
			return false;
		}
		Object obj = hrequest.getSession().getAttribute("SESSION_USER");
		/**
		 * 这里根据系统的登陆session情况来扩展，判断是否需要使用登陆
		 */
		if(obj==null){
			 boolean needSSO = super.needSSO(hrequest, hresponse);
			 if (needSSO) {
				 logger.info("SSO begin");
			 }
			 return needSSO;
		}
		return false;
	}
}
