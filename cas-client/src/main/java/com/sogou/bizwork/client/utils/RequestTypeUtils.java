package com.sogou.bizwork.client.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午05:35:24
 * 类说明
 */
public class RequestTypeUtils {
	private static final String SOGOU_REQUEST_TYPE = "Sogou-Request-Type";
	 private static final String SOGOU_CRM_DWR_REQUEST_PATH = "/dwr";
	    private static List<String> crm_requests = new CopyOnWriteArrayList<String>();
	    
	    static{
	    	crm_requests.add(SOGOU_CRM_DWR_REQUEST_PATH);
	    }
    public static boolean isAjaxRequest(HttpServletRequest hrequest) {
		if(null == hrequest) {
			throw new IllegalArgumentException("param is null.");
		}

		String rtype = hrequest.getHeader(SOGOU_REQUEST_TYPE);
		if(rtype == null || rtype.equals("")){
		    rtype = hrequest.getParameter(SOGOU_REQUEST_TYPE);
		}
		boolean isAjaxReq = false;
		if(null != rtype && "XMLHTTPRequest".equals(rtype)) {
			isAjaxReq = true;
		}

		return isAjaxReq;
	}
    
    public static boolean isDwrRequest(HttpServletRequest hrequest) {
		if(null == hrequest) {
			throw new IllegalArgumentException("param is null.");
		}
		boolean isDwrRequest = false;
		String path = hrequest.getServletPath();
		if(null != path  && PathPatternMatcher.urlPathMatch(crm_requests, path)){
		   isDwrRequest = true;
		}

		return isDwrRequest;
	}

}
