package com.sogou.bizwork.client.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;

import com.sogou.bizwork.client.utils.PathPatternMatcher;


/**
 * Abstract Filter for logout.
 * 
 * @author liujianBJ7368
 * @since 2012-06-26
 */
public abstract class AbstractSingleLogoutFilter extends AbstractConfigurationFilter implements Filter {

	private static final Logger logger = Logger
			.getLogger(AbstractSingleLogoutFilter.class);

	public static final String CONFIG_USE_SSO = "useSSO";
	
	private String artifactParameterName;
	private String logoutParameterName;

	public AbstractSingleLogoutFilter() {
		this.artifactParameterName = "ticket";
		this.logoutParameterName = "logoutRequest";
	}

	public static final String CONFIG_EXCLUDE_PATH = "excludePath";
	private boolean useSSO;
	private List<String> excludePath;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludePathv = getPropertyFromInitParams(filterConfig, CONFIG_EXCLUDE_PATH, "");

		excludePath = new ArrayList<String>();
		if ( null!=excludePathv && !excludePathv.equals("") && excludePathv.length()>0) {
			String[] paths = excludePathv.split(";");
			excludePath.addAll(Arrays.asList(paths));
		}
		
		String useSSOStr = getPropertyFromInitParams(filterConfig, CONFIG_USE_SSO, "true");
		useSSO =  parseBoolean(useSSOStr);
	}
	
	/**
	 * Override following method to reterive session id from a session. 
	 * 
	 * @param session
	 * @return
	 */
	protected String getSessionId(HttpSession session) {
		return session.getId();
	} 
	
	protected HttpSession getSessionByTicketId(String ticketId) {
		return null;
	}

	protected abstract void recordSessionIdByTicketId(String ticketId,
			String sessionId);
	
	protected HttpSession getSessionByTicketIdAndSessionId(String token,
			HttpServletRequest request) {
		return null;
	}

	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		 final HttpServletResponse hresponse = (HttpServletResponse) response;
		String path = hrequest.getServletPath();
		if("".equals(path) || PathPatternMatcher.urlPathMatch(excludePath, path)) {
			chain.doFilter(request, response);
		} else {
			if (needSSO(request, response)) {
				if (isTokenRequest(hrequest)) {
					recordSession(hrequest);
				} else {
					if (isLogoutRequest(hrequest)) {
						destroySession(hrequest);
						
						return;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("Ignoring URI " + hrequest.getRequestURI());
					}
				}
				chain.doFilter(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	/**
	 * @return whether the SSO/SLO process can be by-passed
	 */
	protected boolean needSSO(ServletRequest request, ServletResponse response) {
		return useSSO;
	}
	
	private void destroySession(HttpServletRequest request) {
		String logoutMessage = CommonUtils.safeGetParameter(request,
				this.logoutParameterName);
			logger.info("Logout request:\n\t" + logoutMessage);

		if (CommonUtils.isNotBlank(logoutMessage)) {
			
			/** the follow two method can choose one to destroy session **/
			invalidateSessionBySidAndTicketId(request,logoutMessage);
			invalidateSessionByTicketId(logoutMessage);
		} else {
			logger.warn("Session Index in logout request [" + logoutMessage
					+ "]  is blank.");
		}
	}

	/**
	 * Override <code>invalidateSessionByTicketId</code> to invalidate session according to token (ticket id).
	 * @param token
	 */
	protected void invalidateSessionByTicketId(String token) {
		HttpSession session = getSessionByTicketId(token);
		if (session != null) {
			String sessionID = getSessionId(session);
			if (logger.isDebugEnabled())
				logger.debug("Invalidating session [" + sessionID
						+ "] for token [" + token + "]");
			try {
				session.invalidate();
			} catch (IllegalStateException e) {
				logger.warn("Error invalidating session [" + sessionID
						+ "] for token [" + token + "]", e);
			}
		} else {
			logger.warn("Unalbe to find session according to token [" + token + "]");
		}
	}

	/**
	 * Override <code>invalidateSessionBySidAndTicketId</code> to invalidate session according to sessionId and token (ticket id).
	 * @param token
	 */
	protected void invalidateSessionBySidAndTicketId(HttpServletRequest request,
			String token) {
		HttpSession session = getSessionByTicketIdAndSessionId(token,request);
		if (session != null) {
			String sessionID = getSessionId(session);
			if (logger.isDebugEnabled())
				logger.debug("Invalidating session [" + sessionID
						+ "] for token [" + token + "]");
			try {
				session.invalidate();
			} catch (IllegalStateException e) {
				logger.warn("Error invalidating session [" + sessionID
						+ "] for token [" + token + "]", e);
			}
		}  
	}

	private void recordSession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String token = CommonUtils.safeGetParameter(request,
				this.artifactParameterName);

		try {
			recordSessionIdByTicketId(token, getSessionId(session));
			if (logger.isDebugEnabled()) {
				logger.debug("Recording session for token " + token
						+ "; shared session id is " + getSessionId(session));
			}
		} catch (Exception e) {
			logger.warn("Unable to record session for token " + token
					+ "; shared session id is " + getSessionId(session), e);
		}

	}

	public boolean isTokenRequest(HttpServletRequest request) {
		return CommonUtils.isNotBlank(CommonUtils.safeGetParameter(request,
				this.artifactParameterName));
	}

	public boolean isLogoutRequest(HttpServletRequest request) {
		return CommonUtils
					.isNotBlank(CommonUtils.safeGetParameter(request,
							this.logoutParameterName));
	}

	
	@Override
	public void destroy() {
	}
}
