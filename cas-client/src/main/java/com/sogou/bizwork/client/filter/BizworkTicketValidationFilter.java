package com.sogou.bizwork.client.filter;

import java.util.Timer;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.client.validation.Assertion;

import com.sogou.bizwork.client.utils.CommonUtils;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午06:44:28 类说明
 */
public class BizworkTicketValidationFilter extends
		BizworkAbstractTicketValidationFilter {
	private static final Logger logger = Logger
			.getLogger(BizworkTicketValidationFilter.class);

	protected void initInternal(final FilterConfig filterConfig)
			throws ServletException {
	}
	
	public void init() {
		super.init();
		CommonUtils.assertNotNull(this.authenticationLogger,
				"authenticationLogger cannot be null.");
		if (this.timer == null) {
			this.timer = new Timer(true);
		}
		this.timer.schedule(this.timerTask, this.millisBetweenCleanUps,
				this.millisBetweenCleanUps);
	}
	
	public void destroy() {
		super.destroy();
		if(timer != null){
			this.timer.cancel();
		}
	}
	
	@Override
	protected void onFailedValidation(HttpServletRequest request,
			HttpServletResponse response) {
		authenticationLogger.authenticationFailed(request, response);
	}

	@Override
	protected void onSuccessfulValidation(HttpServletRequest request,
			HttpServletResponse response, Assertion assertion) {
		authenticationLogger
				.authenticationSuccess(request, response, assertion);
	}
}
