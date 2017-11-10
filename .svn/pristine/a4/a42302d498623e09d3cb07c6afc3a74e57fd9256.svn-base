package com.sogou.bizwork.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.util.IPUtils;
import com.sogou.bizwork.util.PathPatternMatcher;


public class LogFilter implements Filter {
    
    private final Logger logger = Logger.getLogger(LogFilter.class);
    
    private static final String CONFIG_EXCLUDE_PATH = "excludePath";
    private List<String> excludePath;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePathv = filterConfig.getInitParameter(CONFIG_EXCLUDE_PATH);
        excludePath = new ArrayList<String>();
        if (!StringUtils.isBlank(excludePathv)) {
            String[] paths = excludePathv.split(";");
            excludePath.addAll(Arrays.asList(paths));
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        String userAgent = req.getHeader("User-Agent");
        String cookie = req.getHeader("Cookie");
        String path = req.getServletPath();
        String ip = IPUtils.getIpAddr(req);
        User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
        
        if (PathPatternMatcher.urlPathMatch(excludePath, path)) {
            chain.doFilter(request, response);
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("cookie:[").append(cookie).append("]");
        builder.append("\tuser-agent:[").append(userAgent).append("]");
        builder.append("\tip:[").append(ip).append("]");
        builder.append("\tusername:[").append(user != null ? user.getUsername() : "").append("]");
        builder.append("\tpath:[").append(path).append("]");

        List<String> pairs = new ArrayList<String>();
        Enumeration paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            pairs.add(paramName + "=" + paramValue);
        }
        builder.append("\tparams:[").append(StringUtils.join(pairs, ",")).append("]");
        
        builder.append("\tdebugLogin:[").append(user != null ? user.isDebugLogin() : "").append("]");
        
//        logger.info(builder.toString());
        
        chain.doFilter(request, response);
    }

    public void destroy() {
        
    }

}