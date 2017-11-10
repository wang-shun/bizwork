package com.sogou.bizwork.controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.services.UnauthorizedServiceException;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.TicketValidationException;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.validation.Assertion;
import org.jasig.cas.validation.Cas20ProtocolValidationSpecification;
import org.jasig.cas.validation.ValidationSpecification;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.biztech.starry.dto.UserDTO;
import com.sogou.bizwork.bo.Result;
import com.sogou.bizwork.cas.application.dto.LogonLogDto;
import com.sogou.bizwork.cas.application.model.Application;
import com.sogou.bizwork.cas.application.service.BizworkApplicationService;
import com.sogou.bizwork.cas.application.service.BizworkAuthorizationApplicationService;
import com.sogou.bizwork.cas.application.service.BizworkAuthorizationUserService;
import com.sogou.bizwork.cas.application.service.LogonLogService;
import com.sogou.bizwork.cas.constants.AuthenticationLogHelper;
import com.sogou.bizwork.cas.constants.SSOConstantsCode;
import com.sogou.bizwork.cas.constants.SSOLogConstantsCode;
import com.sogou.bizwork.cas.constants.ThreadLocalHolder;
import com.sogou.bizwork.cas.logonLog.LogonLogThreadHandler;
import com.sogou.bizwork.cas.principal.BizworkTicketGrantingTicketImpl;
import com.sogou.bizwork.cas.user.model.User;
import com.sogou.bizwork.cas.user.service.UserManageService;
import com.sogou.bizwork.cas.user.service.UserService;
import com.sogou.bizwork.cas.utils.BizworkCredentials;
import com.sogou.bizwork.cas.utils.JasonUtils;
import com.sogou.bizwork.cas.utils.PCredentials;
import com.sogou.bizwork.cas.utils.StringUtils;
import com.sogou.bizwork.cas.web.support.BizworkWebApplicationServiceImpl;

/**
 * bizwork login
 * 
 * @author sixiaolin
 *
 */
@Controller
@RequestMapping("/cas")
@SuppressWarnings("unchecked")
public class SSOLoginController extends AbstractSSOController implements SSOConstantsCode {

    public static final String BIZWORK_LOG_NAME = "com.sogou.bizwork.cas.controller.SSOLoginController";

    private static final String LOTOUT_PARAM_NAME = "logoutRequest";

    private static final Logger loginLog = Logger.getLogger(BIZWORK_LOG_NAME);

    /** The validation protocol we want to use. */
    @NotNull
    private Class<?> validationSpecificationClass = Cas20ProtocolValidationSpecification.class;
    @NotNull
    @Autowired
    private CentralAuthenticationService bizworkAuthenticationService;

    @NotNull
    @Autowired
    private BizworkAuthorizationUserService bizworkAuthorizationUserService;

    
    @NotNull
    @Autowired 
    private TicketRegistry ticketRegistry;

    @NotNull
    @Autowired 
    private TicketRegistry bizworkTicketRegistry;
     

    @NotNull
    @Autowired
    private ArgumentExtractor argumentExtractor;
    @NotNull
    @Autowired
    private BizworkApplicationService bizworkApplicationService;
    @NotNull
    @Autowired
    private BizworkAuthorizationApplicationService bizWorkAuthorizationApplicationService;

    @NotNull
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private UserService userService;

    @NotNull
    @Autowired
    private LogonLogService logonLogService;

    final static int BAD_LOGIN_THRESHOLD = 5;

    final static int TICKET_USED_NUMBER = 60;

    final static int BAD_LOGIN_WAIT_TIME = 1000 * 300;


    @RequestMapping(value = "/login.do")
    @ResponseBody
    protected Result login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final WebApplicationService service = this.argumentExtractor.extractService(request);
        final String tgt = request.getParameter("TGT");
        final String sid = request.getParameter("sid");
        final String clientIp = request.getRemoteAddr();

        Result responseResult = new Result();
        Boolean toGrantTicket = true;

        if (StringUtils.isEmpty(tgt) || StringUtils.isEmpty(sid) || service == null) {
            toGrantTicket = false;
            loginLog.info("sid||service is empty " + request.getQueryString());
            responseResult.setSuccess(false);
            responseResult.setErrorMsg("tgt||sid||service is empty");
            return responseResult;
        }

        try {

            Application application = bizworkApplicationService.seleApplicationByAppId(service.getId());
            if (application == null) {
                toGrantTicket = false;
                loginLog.info("service is not registerd,service= " + service.getId());
                responseResult.setSuccess(false);
                responseResult.setErrorMsg("service is not registerd,service= " + service.getId());
                return responseResult;
            }

            // TicketGrantingTicket
            // tgtGrantingTicket=(TicketGrantingTicket)ticketRegistry.getTicket(tgt);
            TicketGrantingTicket tgtGrantingTicket = (TicketGrantingTicket) ticketRegistry.getTicket(tgt);
            if (tgtGrantingTicket == null) {
                toGrantTicket = false;
                loginLog.info("tgtGrantingTicket can not be found,tgt= " + tgt);
                responseResult.setSuccess(false);
                responseResult.setErrorMsg("tgtGrantingTicket can not be found,tgt= " + tgt);
                return responseResult;
            }

            BizworkCredentials credentials = (BizworkCredentials) bizworkAuthorizationUserService
                    .queryCredentialsByTgt(tgt);

            if (credentials == null) {
                toGrantTicket = false;
                loginLog.info("credentials can not be found by tgt,tgt= " + tgt);
                responseResult.setSuccess(false);
                responseResult.setErrorMsg("credentials can not be found by tgt,tgt= " + tgt);
                return responseResult;
            }

            User user = userManageService.isExsitUserAndObtainPrincipal(credentials.getUsername());

            if (user == null) {
                toGrantTicket = false;
                loginLog.info("user is not found username=" + credentials.getUsername());
                responseResult.setSuccess(false);
                responseResult.setErrorMsg("user is not found username=" + credentials.getUsername());
                return responseResult;
            }

            if (toGrantTicket) {
                final String serviceTicketId = this.bizworkAuthenticationService.grantServiceTicket(tgt, service);

                bizworkAuthorizationUserService.updateSidByServiceTicket(serviceTicketId, sid);
                loginLog.info("serviceTicket is gennerated :" + serviceTicketId + " under TGT " + tgt + " user id "
                        + credentials.getUsername());

                /**
                 * ThreadLocalHolder bind
                 */
                ThreadLocalHolder.bindProperty(SSOConstantsCode.CURRENT_LOGIN_USER, user);
                ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SERVICE, service.getId());
                ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SID, request.getSession().getId());
                ThreadLocalHolder.bindProperty(CURRENT_LOGIN_APPLICATION, application);

                /**
                 * 增加日志
                 */

                Long userId = Long.valueOf(user.getId() != null ? user.getId().toString() : "0");

                final String sessionId = request.getSession().getId();

                AuthenticationLogHelper.appAuthenticationLog(SSOLogConstantsCode.AUTHENTICATION_SUCCESS,
                        SSOLogConstantsCode.AUTHENTICATION_SUCCESS, (String) service.getId(), (String) sessionId,
                        userId, user.getEmail(), "xx", "Successful login", clientIp);
                /**
                * LogonLog
                */
                updateLoginLog(userId, credentials.getUsername(), credentials.getPassword(), " ", 0, service.getId(),
                        clientIp);

                responseResult.setSuccess(true);
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("ST", serviceTicketId);
                params.put("service", service.getId());
                responseResult.setData(params);

            }
            
        } catch (final TicketException e) {
            loginLog.error("Gen service ticket error:" + e.getMessage());
            responseResult.setSuccess(false);
            responseResult.setErrorMsg("Gen service ticket error:");
            return responseResult;
        }
        return responseResult;
    }

	@RequestMapping("/authenticate.do")
    @ResponseBody
    protected Result authenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebApplicationService service = this.argumentExtractor.extractService(request);
        String userName = "";
        String password = "";
        String sid = "";
        String clientIp = request.getRemoteAddr();
        if ("GET".equals(request.getMethod())) {
            service = this.argumentExtractor.extractService(request);
            userName = request.getParameter("username");
            password = request.getParameter("password");
            sid = request.getParameter("sid");

        } else if ("POST".equals(request.getMethod())) {
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) {
                loginLog.error("get authenticate param error :" + e.getMessage());
            }

            HashMap<String, String> paramMap = (HashMap<String, String>) JasonUtils.getMapFromJson(jb.toString());
            userName = paramMap.get("username");
            password = paramMap.get("password");

            sid = paramMap.get("sid");
            String serviceUrl = paramMap.get("service");
            service = new BizworkWebApplicationServiceImpl(serviceUrl);
        }
        Result responseResult = new Result();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(sid)
                || service == null) {
            String message = "username||password||sid||service is empty " + request.getQueryString();
            responseResult = loginFailed("参数不全", message);
            return responseResult;
        }

        try {
            Application application = bizworkApplicationService.seleApplicationByAppId(service.getId());
            if (application == null) {
                String message = "service is not registerd,service= " + service.getId();
                responseResult = loginFailed("请求服务不存在", message);
                return responseResult;

            }

            BizworkCredentials credentials = new BizworkCredentials();
            credentials.setUsername(userName);
            credentials.setPassword(password);

            User user = userManageService.isExsitUserAndObtainPrincipal(credentials.getUsername());
            if (user == null || user.getId() == null) {
                String message = "user is not exist";
                loginLog.error(message);
                responseResult = loginFailed("用户不存在", message);
                return responseResult;
            }

            /**
             * ThreadLocalHolder bind
             */
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SERVICE, service.getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SID, request.getSession().getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_APPLICATION, application);

            String ticketGrantingTicket = this.bizworkAuthenticationService.createTicketGrantingTicket(credentials);
            loginLog.info("ticketGrantingTicket created:" + ticketGrantingTicket + "for user "
                    + credentials.getUsername());
            // 缓存TGT和credential
            bizworkAuthorizationUserService.updateCredentialsByTgt(ticketGrantingTicket, credentials);

            final String serviceTicketId = this.bizworkAuthenticationService.grantServiceTicket(ticketGrantingTicket,
                    service, credentials);
            loginLog.info("serviceTicket created:" + serviceTicketId + "user tgt " + ticketGrantingTicket + "for user "
                    + credentials.getUsername());
            // 缓存sid 和 ST
            bizworkAuthorizationUserService.updateSidByServiceTicket(serviceTicketId, sid);

            /**
             * ThreadLocalHolder bind
             */
            ThreadLocalHolder.bindProperty(SSOConstantsCode.CURRENT_LOGIN_USER, user);
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SERVICE, service.getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SID, request.getSession().getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_APPLICATION, application);

            /**
             * 增加日志
             */

            Long userId = Long.valueOf(user.getId() != null ? user.getId().toString() : "0");

            final String sessionId = request.getSession().getId();

            AuthenticationLogHelper.appAuthenticationLog(SSOLogConstantsCode.AUTHENTICATION_SUCCESS,
                    SSOLogConstantsCode.AUTHENTICATION_SUCCESS, (String) service.getId(), (String) sessionId, userId,
                    user.getEmail(), "xx", "Successful login", clientIp);
            /**
            * LogonLog
            */
            updateLoginLog(userId, credentials.getUsername(), credentials.getPassword(), " ", 0, service.getId(),
                    clientIp);

            responseResult.setSuccess(true);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("ST", serviceTicketId);
            params.put("service", service.getId());
            params.put("ST", serviceTicketId);
            params.put("TGT", ticketGrantingTicket);
            
            responseResult.setData(params);
        	String ip = this.getIpAddr(request);
            
//            userService.updateLastLoginTime(user.getUserName());
        } catch (final TicketException e) {
            String message = "authenticate error:" + e.getMessage();
            responseResult = loginFailed("登录失败", message);
            return responseResult;
        }
        return responseResult;

    }
    private String getIpAddr(HttpServletRequest request) { 
    	String ip = request.getHeader("x-forwarded-for"); 
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    	ip = request.getHeader("Proxy-Client-IP"); 
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    	ip = request.getHeader("WL-Proxy-Client-IP"); 
    	} 
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    	ip = request.getRemoteAddr(); 
    	} 
    	return ip; 
    } 

    @RequestMapping("/serviceValidate.do")
    @ResponseBody
    protected Result serviceValidate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final WebApplicationService service = this.argumentExtractor.extractService(request);
        final String serviceTicket = request.getParameter("ST");

        Result result = new Result();

        if (service == null || StringUtils.isEmpty(serviceTicket)) {
            String message = "INVALID_REQUEST";
            result = loginFailed(message, message);
            return result;
        }
        try {
            // 小P登录修改一小部分validateServiceTicket方法代码，主要是为了兼容小P，对之前验证没有影响。
            final Assertion assertion = this.bizworkAuthenticationService.validateServiceTicket(serviceTicket, service);
            final ValidationSpecification validationSpecification = this.getCommandClass();
            final ServletRequestDataBinder binder = new ServletRequestDataBinder(validationSpecification,
                    "validationSpecification");
            initBinder(request, binder);
            binder.bind(request);

            if (!validationSpecification.isSatisfiedBy(assertion)) {
                String message = "ServiceTicket [" + serviceTicket + "] does not satisfy validation specification.";
                result = loginFailed(message, message);
                return result;
            }
            loginLog.info(" serviceValidate success for " + serviceTicket);
            result.setSuccess(true);
            result.setData(assertion.getChainedAuthentications().get(0).getAttributes());
            return result;
        } catch (final TicketValidationException e) {
            String message = "Code:" + e.getCode() + " serviceTicketId:" + serviceTicket + " service:"
                    + service.getId() + " Message:" + e.getMessage();
            result = loginFailed(message, message);
            return result;
        } catch (final TicketException te) {
            String message = "Code:" + te.getCode() + " serviceTicketId:" + serviceTicket + " service:"
                    + service.getId() + " Message:" + te.getMessage();
            result = loginFailed(message, message);
            return result;
        } catch (final UnauthorizedServiceException e) {
            String message = "serviceTicketId:" + serviceTicket + " service:" + service.getId() + " Message:"
                    + e.getMessage();
            result = loginFailed(message, message);
            return result;
        }
    }

    @RequestMapping("/logout.do")
    @ResponseBody
    protected Result logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result result = new Result();
        final String ticketGrantingTicket = request.getParameter("TGT");

        if (!StringUtils.isEmpty(ticketGrantingTicket)) {
            try {
            	TicketGrantingTicket tgt = (TicketGrantingTicket)bizworkTicketRegistry.getTicket(ticketGrantingTicket);
                if (tgt == null) {
                    request.getSession().invalidate();
                    String message = ticketGrantingTicket + "has been expired";
                    loginFailed("会话已过期", message);
                    result.setSuccess(true);
                    return result;
                }
                BizworkTicketGrantingTicketImpl dtgt = (BizworkTicketGrantingTicketImpl) tgt;
                if (dtgt != null) {
                    this.bizWorkAuthorizationApplicationService.clearLogonServiceTypeByTgt(ticketGrantingTicket);
                }
                loginLog.info("TGT " + dtgt.getId() + " has been logedout ");
                ConcurrentHashMap<String, Service> services = dtgt.getLoginServices();

                ArrayList<String> logoutUrls = new ArrayList<String>();
                if (!services.isEmpty()) {
                    for (Entry<String, Service> service : services.entrySet()) {
                        String ticket = service.getKey();
                        String logoutService = service.getValue().getId();
                        logoutUrls.add(logoutService + "?" + LOTOUT_PARAM_NAME + "=" + ticket);

                        loginLog.info("TGT " + dtgt.getId() + " has been logedout for service " + logoutService);
                    }
                }
                this.bizworkAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicket);
                request.getSession().invalidate();
                result.setSuccess(true);
                result.setData(logoutUrls);
            } catch (Exception e) {
                String message = "logout failed," + e.getMessage();
                result = loginFailed("登出失败", message);
            }
        } else {
            request.getSession().invalidate();
            result.setSuccess(true);
            // result=loginFailed("TGT为空",message);
        }

        return result;
    }

    // 小P认证接口，为了应对小P登录，主要增加的东西就是PCredentials和XiaoPAuthenticationHandler
	// 小P接入统一登录主要是使用了这个接口和上面的/serviceValidate.do接口，这个接口接收从前端传过来的代表小P用户的token和代表想要访问的应用service。
    // 验证token的有效性，主要是通过回调小P提供的接口验证token的有效性，如果能够通过token得到username等属性说明该token是有效的。接收service主要是为了
    // 生成对应的TGT和ST。传给前端的数据也就是TGT和ST
    @RequestMapping("/authenticateXiaoP.do")
    @ResponseBody
    protected Result authenticateXiaoP(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebApplicationService service = this.argumentExtractor.extractService(request);
        String token = "";
        String sid = "";
        String clientIp = request.getRemoteAddr();
        if ("GET".equals(request.getMethod())) {
            service = this.argumentExtractor.extractService(request);
            token = request.getParameter("token");
            sid = request.getParameter("sid");
        } else if ("POST".equals(request.getMethod())) {
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) {
                loginLog.error("get authenticateXiaoP param error :" + e.getMessage());
            }

            HashMap<String, String> paramMap = (HashMap<String, String>) JasonUtils.getMapFromJson(jb.toString());
            token = paramMap.get("token");
            sid = paramMap.get("sid");
            String serviceUrl = paramMap.get("service");
            service = new BizworkWebApplicationServiceImpl(serviceUrl);
        }

        Result responseResult = new Result();

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(sid) || service == null) {
            String message = "token||sid||service is empty " + request.getQueryString();
            responseResult = loginFailed("参数不全", message);
            return responseResult;
        }

        try {
            Application application = bizworkApplicationService.seleApplicationByAppId(service.getId());
            if (application == null) {
                String message = "service is not registerd,service= " + service.getId();
                responseResult = loginFailed("请求服务不存在", message);
                return responseResult;
            }
            String username = "";
            try {
                // 该方法就是通过前端传过来的token得到username。
                username = userManageService.getUsernameByXiaoP(token);
                username += "@sogou-inc.com";
            } catch (Exception e) {
                String message = "authenticate error: fail to get username by xiaoP token ";
                responseResult = loginFailed("认证失败", message);
                return responseResult;
            }

            if ("".equals(username)) {
                String message = "authenticate error: fail to get username by xiaoP token ";
                responseResult = loginFailed("认证失败", message);
                return responseResult;
            }
            // 增加的认证类，还有就是增加了另一个认证方法专门认证小P的，可以在配置文件中查看
            PCredentials credentials = new PCredentials();
            credentials.setUsername(username);
            credentials.setToken(token);

            User user = userManageService.isExsitUserAndObtainPrincipal(credentials.getUsername());
            if (user == null || user.getId() == null) {
                String message = "user is not exist";
                loginLog.error(message);
                responseResult = loginFailed("用户不存在", message);
                return responseResult;
            }

            /**
             * ThreadLocalHolder bind
             */
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SERVICE, service.getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SID, request.getSession().getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_APPLICATION, application);

            // 产生tgt
            String ticketGrantingTicket = this.bizworkAuthenticationService.createTicketGrantingTicket(credentials);
            ticketGrantingTicket = ticketGrantingTicket.trim();
            loginLog.info("ticketGrantingTicket created:" + ticketGrantingTicket + " for user "
                    + credentials.getUsername());
            // 缓存TGT和credential
            bizworkAuthorizationUserService.updatePCredentialsByTgt(ticketGrantingTicket, credentials);

            final String serviceTicketId = this.bizworkAuthenticationService.grantServiceTicket(ticketGrantingTicket,
                    service, credentials);
            loginLog.info("serviceTicket created:" + serviceTicketId + " user tgt " + ticketGrantingTicket + "for user "
                    + credentials.getUsername());
            // 缓存sid 和 ST
            bizworkAuthorizationUserService.updateSidByServiceTicket(serviceTicketId, sid);

            /**
             * ThreadLocalHolder bind
             */
            ThreadLocalHolder.bindProperty(SSOConstantsCode.CURRENT_LOGIN_USER, user);
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SERVICE, service.getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_SID, request.getSession().getId());
            ThreadLocalHolder.bindProperty(CURRENT_LOGIN_APPLICATION, application);

            /**
             * 增加日志
             */

            Long userId = Long.valueOf(user.getId() != null ? user.getId().toString() : "0");

            final String sessionId = request.getSession().getId();

            AuthenticationLogHelper.appAuthenticationLog(SSOLogConstantsCode.AUTHENTICATION_SUCCESS,
                    SSOLogConstantsCode.AUTHENTICATION_SUCCESS, (String) service.getId(), (String) sessionId, userId,
                    user.getEmail(), "xx", "Successful login", clientIp);
            /**
            * LogonLog
            */
            updateLoginLog(userId, credentials.getUsername(), credentials.getToken(), " ", 0, service.getId(), clientIp);

            responseResult.setSuccess(true);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("service", service.getId());
            params.put("ST", serviceTicketId);
            params.put("TGT", ticketGrantingTicket);
            responseResult.setData(params);

        } catch (final TicketException e) {
            String message = "authenticate error:" + e.getMessage();
            responseResult = loginFailed("登录失败", message);
            return responseResult;
        }
        return responseResult;

    }

    public BizworkAuthorizationApplicationService getEunomiaAuthorizationApplicationService() {
        return bizWorkAuthorizationApplicationService;
    }

    public void setEunomiaAuthorizationApplicationService(
            BizworkAuthorizationApplicationService eunomiaAuthorizationApplicationService) {
        this.bizWorkAuthorizationApplicationService = eunomiaAuthorizationApplicationService;
    }

    public ArgumentExtractor getArgumentExtractor() {
        return argumentExtractor;
    }

    public void setArgumentExtractor(ArgumentExtractor argumentExtractor) {
        this.argumentExtractor = argumentExtractor;
    }

    public LogonLogService getLogonLogService() {
        return logonLogService;
    }

    public void setLogonLogService(LogonLogService logonLogService) {
        this.logonLogService = logonLogService;
    }

    public Class<?> getValidationSpecificationClass() {
        return validationSpecificationClass;
    }

    public void setValidationSpecificationClass(Class<?> validationSpecificationClass) {
        this.validationSpecificationClass = validationSpecificationClass;
    }

    public CentralAuthenticationService getBizworkAuthenticationService() {
        return bizworkAuthenticationService;
    }

    public void setBizworkAuthenticationService(CentralAuthenticationService bizworkAuthenticationService) {
        this.bizworkAuthenticationService = bizworkAuthenticationService;
    }

    public BizworkApplicationService getBizworkApplicationService() {
        return bizworkApplicationService;
    }

    public void setBizworkApplicationService(BizworkApplicationService bizworkApplicationService) {
        this.bizworkApplicationService = bizworkApplicationService;
    }

    public BizworkAuthorizationApplicationService getBizWorkAuthorizationApplicationService() {
        return bizWorkAuthorizationApplicationService;
    }

    public void setBizWorkAuthorizationApplicationService(
            BizworkAuthorizationApplicationService bizWorkAuthorizationApplicationService) {
        this.bizWorkAuthorizationApplicationService = bizWorkAuthorizationApplicationService;
    }

    public BizworkAuthorizationUserService getBizworkAuthorizationUserService() {
        return bizworkAuthorizationUserService;
    }

    public void setBizworkAuthorizationUserService(BizworkAuthorizationUserService bizworkAuthorizationUserService) {
        this.bizworkAuthorizationUserService = bizworkAuthorizationUserService;
    }

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    private void updateLoginLog(Long userId, String userName, String password, String userStatus, Integer userType,
            String serviceUrl, String clientIp) {
        try {
            LogonLogDto logonLogDto = new LogonLogDto();
            logonLogDto.setAccountid(userId);
            logonLogDto.setAccountname(userName);
            logonLogDto.setUserType(userType);
            logonLogDto.setUserip(clientIp);

            if (clientIp != null && clientIp.length() > 15) {
                loginLog.warn("ClientIP " + "\t" + clientIp);
                String userip = "";
                if (clientIp.indexOf(",") != -1) {
                    userip = clientIp.substring(0, clientIp.indexOf(","));
                    if (userip.length() > 15) {
                        userip = userip.substring(0, 15);
                    }
                } else {
                    userip = clientIp.substring(0, 15);
                }
                logonLogDto.setUserip(userip);
            }

            logonLogDto.setServiceurl(serviceUrl);
            logonLogDto.setStatus(userStatus);

            LogonLogThreadHandler.createLogonLog(logonLogService, userId, password, logonLogDto);
        } catch (Exception e) {
            loginLog.warn("Unable to Update LogonLog userId=" + userId + ",userName=" + userName + ",userStatus="
                    + userStatus + ",serviceUrl=" + serviceUrl);
        }
    }

    private Result loginFailed(String showMessage, String logmMessage) {
        loginLog.error(logmMessage);
        Result result = new Result();
        result.setSuccess(false);
        result.setErrorMsg(showMessage);
        return result;
    }

    private ValidationSpecification getCommandClass() {
        try {
            return (ValidationSpecification) this.validationSpecificationClass.newInstance();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        binder.setRequiredFields(new String[] { "renew" });
    }

    public TicketRegistry getBizworkTicketRegistry() {
        return bizworkTicketRegistry;
    }

    public void setBizworkTicketRegistry(TicketRegistry bizworkTicketRegistry) {
        this.bizworkTicketRegistry = bizworkTicketRegistry;
    }

	public TicketRegistry getTicketRegistry() {
		return ticketRegistry;
	}

	public void setTicketRegistry(TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}
    
}