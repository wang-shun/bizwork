package com.sogou.bizwork.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.util.IPUtils;

public class ExecuteTimeInterceptor implements HandlerInterceptor {
	private final Logger executeTimeLog = Logger.getLogger("EXECUTETIME");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ExecuteTimeHolder.setStartTime(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String path = request.getServletPath();
        String ip = IPUtils.getIpAddr(request);
        
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
        long endTime = System.currentTimeMillis();
        long startTime = ExecuteTimeHolder.getStartTime();
        long executeTime = endTime - startTime;
        
        StringBuilder builder = new StringBuilder();
        builder.append("ip:[").append(ip).append("]");
        builder.append("\tusername:[").append(user != null ? user.getUsername() : "").append("]");
        builder.append("\tpath:[").append(path).append("]");
        
        List<String> pairs = new ArrayList<String>();
        Map paraMap = request.getParameterMap();
        Set keySet = paraMap.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String paraName = (String) it.next();
            String paraValue = ((String[]) paraMap.get(paraName))[0];
            pairs.add(paraName + "=" + paraValue);
        }
        builder.append("\tparams:[").append(StringUtils.join(pairs, ",")).append("]");
        
        builder.append("\texecuteTime:[").append(executeTime).append("]");

        executeTimeLog.info(builder.toString());
    }

}
