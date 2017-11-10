package com.sogou.bizwork.client.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.validation.Assertion;

import com.sogou.bizwork.client.utils.CommonUtils;
import com.sogou.bizwork.client.utils.RequestTypeUtils;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午05:06:11 类说明
 */
public class BizworkAuthenticationFilter extends BizworkAbstractConfigFilter {

	private static final Logger logger = Logger
			.getLogger(BizworkAuthenticationFilter.class);

	protected void initInternal(final FilterConfig filterConfig)
			throws ServletException {

	}
	
	public void init() {
		super.init();
	}
	
	@Override
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request,getArtifactParameterName());

        if (CommonUtils.isNotBlank(ticket)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String modifiedServiceUrl;
        logger.info("no ticket and no assertion found");
        modifiedServiceUrl = serviceUrl;
        logger.info("Constructed service url: " + modifiedServiceUrl);
        final String urlToRedirectTo = constructRedirectUrlWithNonce(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway, this.nonceName);
        logger.info("redirecting to \"" + urlToRedirectTo + "\"");
        authenticationLogger.authenticationBegin(request, response);
        
        boolean isAjaxReq = RequestTypeUtils.isAjaxRequest(request);
        boolean isCrmDwrReq = RequestTypeUtils.isDwrRequest(request);
        if(isAjaxReq || isCrmDwrReq){
        	if(logger.isDebugEnabled()){
        		logger.info("it is a XMLHTTPRequest redirect to eunomia.");
        	}
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		response.getWriter().write("XMLHTTRequest");
    		return;
        }else{
        	response.sendRedirect(urlToRedirectTo);	
        	return;
        }
    }
    
    public final String getServiceParameterName() {
        return this.serviceParameterName;
    }
    
	protected static String constructRedirectUrlWithNonce(
			String casServerLoginUrl, String serviceParameterName,
			String serviceUrl, boolean renew, boolean gateway, String nonceName) {
		try {
			StringBuilder sb = new StringBuilder(128);
			sb.append(casServerLoginUrl);
			sb.append(((casServerLoginUrl.indexOf("?") != -1) ? "&" : "?"));
			sb.append(serviceParameterName).append("=").append(URLEncoder.encode(serviceUrl, "UTF-8"));
			sb.append("&").append(nonceName).append("=").append(System.currentTimeMillis());
			return sb.toString();
			 
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
