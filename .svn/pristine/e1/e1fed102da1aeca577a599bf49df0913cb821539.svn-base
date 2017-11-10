package com.sogou.bizwork.client.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.validation.Assertion;

import com.github.inspektr.common.web.ClientInfo;
import com.github.inspektr.common.web.ClientInfoHolder;
import com.sogou.bizwork.cas.constants.UUCConstants;
import com.sogou.bizwork.cas.utils.StringUtils;
import com.sogou.bizwork.client.dto.LoginUserExample;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-20 下午01:43:31
 * 类说明
 */
public class ExampleBizworkUserFilter extends AbstractConfigurationFilter implements Filter,
UUCConstants {


	private static final long serialVersionUID = -3920403250590755433L;
	private Logger logger = Logger.getLogger(getClass());
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static String BIZWORK_ACTIVE_SESSION = AbstractCasFilter.CONST_CAS_ASSERTION;
	private static String BIZWORK_USER_LOGINED = "_bizwork_user_logined";
	public static final String CONFIG_USE_SSO = "useSSO";
	protected boolean enableSSO = true;
	
	public static final String CONFIG_EXCLUDE_PATH = "excludePath";
	private List<String> excludePath;

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		boolean visited = false;
		try {
			if ( enableSSO && session.getAttribute(BIZWORK_ACTIVE_SESSION) != null ) {
				if ((Boolean) session.getAttribute(BIZWORK_USER_LOGINED) != null
						&& (Boolean) session.getAttribute(BIZWORK_USER_LOGINED) == true) {
					filterChain.doFilter(request, response);
				} else {
					Object object = session
							.getAttribute(BIZWORK_ACTIVE_SESSION);
					Assertion assertion = (Assertion) object;
					Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
					if (null!=attributes && attributes.size() > 0) {
						
						LoginUserExample account = getAccount(attributes);
						session.setAttribute("session_User",	account);
						Cookie namecookie = new Cookie("aname", account.getEmail());
						response.addCookie(namecookie);
						try {
							ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
							 log.info("account "+account.getId()+"has login success");
						} catch (Throwable t) {
							logger.warn("Error in writing authentication log.",
									t);
						} finally {
						}
						logger.info("user [" + account.getId() + "/"
								+ account.getEmail() + "] has login in dubhe");
					}
					visited = true;
					session.setAttribute(BIZWORK_USER_LOGINED, visited);
					filterChain.doFilter(request, response);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String useSSOStr = getPropertyFromInitParams(filterConfig, CONFIG_USE_SSO, "true");
		enableSSO =  parseBoolean(useSSOStr);
		String excludePathv = filterConfig
				.getInitParameter(CONFIG_EXCLUDE_PATH);
		excludePath = new ArrayList<String>();
		if (!StringUtils.isBlank(excludePathv)) {
			String[] paths = excludePathv.split(";");
			excludePath.addAll(Arrays.asList(paths));
		}
	}

	private LoginUserExample getAccount(Map<String, Object> attributes) {
		LoginUserExample account = new LoginUserExample();
		try {
			account.setId(Integer.decode((String.valueOf(attributes.get(USER_ID)))));
			account.setEmail(URLDecoder.decode(String.valueOf(attributes.get(USER_EMAIL)), DEFAULT_ENCODING));
			account.setJob(URLDecoder.decode(String.valueOf(attributes.get(USER_TITLE)), DEFAULT_ENCODING));
			account.setLevel(URLDecoder.decode(String.valueOf(attributes.get(USER_LEVEL)), DEFAULT_ENCODING));
			account.setChineseName(URLDecoder.decode(String.valueOf(attributes.get(USER_NAME)), DEFAULT_ENCODING));
			account.setGroupName(URLDecoder.decode(String.valueOf(attributes.get(USER_GROUP)), DEFAULT_ENCODING));
			return account;
		} catch (Exception e) {
			logger.warn("Error in assembly account.", e);
			return null;
		}

	}

	@Override
	public void destroy() {

	}

}
