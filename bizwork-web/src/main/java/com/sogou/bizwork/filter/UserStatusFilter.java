package com.sogou.bizwork.filter;

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

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sogou.bizwork.bo.User;
import com.sogou.bizwork.constant.Constant;
import com.sogou.bizwork.constant.ErrorEnum;
import com.sogou.bizwork.util.PathPatternMatcher;

/**
 * 用户状态管理
 * @author dongzeguang
 *
 */
public class UserStatusFilter implements Filter {

    private final Logger logger = Logger.getLogger(this.getClass());

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CONFIG_EXCLUDE_PATH = "excludePath";
    private WebApplicationContext ctx = null;

    private List<String> excludePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());

        String excludePathv = filterConfig.getInitParameter(CONFIG_EXCLUDE_PATH);
        excludePath = new ArrayList<String>();
        if (!StringUtils.isBlank(excludePathv)) {
            String[] paths = excludePathv.split(";");
            excludePath.addAll(Arrays.asList(paths));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String path = req.getServletPath();

        if (PathPatternMatcher.urlPathMatch(excludePath, path)) {
            chain.doFilter(request, response);
            return;
        }

        User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
        if (user == null) {
            logger.info("session user is null, maybe not login or expired, path:[" + path + "]");
            session.invalidate();
            this.handleSessionExpired(req, resp, ErrorEnum.LOGIN_EXPIRE_ERROR);
            return;
        } else {
            String debugLogin = (String) session.getAttribute(Constant.DEBUG_LOGIN_KEY);
            if (debugLogin != null && debugLogin.equals(Constant.TRUE)) {
                chain.doFilter(request, response);
                return;
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {}

    /**
     * 登录失效时的处理，如果是来自app的请求，以json格式返回
     * @param request
     * @param response
     * @param error
     * @throws IOException
     */
    private void handleSessionExpired(HttpServletRequest request, HttpServletResponse response, ErrorEnum error)
            throws IOException {
        this.writeErrorResult(request, response, error);
    }

    private void writeErrorResult(HttpServletRequest request, HttpServletResponse response, ErrorEnum error)
            throws IOException {
        JSONObject json = new JSONObject();
        String sid = (String) request.getAttribute("sid");
        json.put("success", false);
        json.put("data", null);
        json.put("sid", sid);
        json.put("errorCode", error.getCode());
        json.put("errorMsg", error.getMessage());
        response.setContentType(CONTENT_TYPE);
        response.getWriter().print(json.toString());
    }

}
