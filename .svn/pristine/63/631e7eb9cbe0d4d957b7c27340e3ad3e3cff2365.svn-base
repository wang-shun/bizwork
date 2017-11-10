package com.sogou.bizwork.cas.authentication;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.principal.Credentials;
import org.springframework.beans.factory.annotation.Autowired;

import com.sogou.bizwork.cas.user.service.UserManageService;
import com.sogou.bizwork.cas.utils.PCredentials;

/**
 * Delegate authentication into specified <code>AuthenticationHandler</code>
 * 
 * @author liujianBJ7368
 * @since 2012-08-24
 */
public class XiaoPAuthenticationHandler implements AuthenticationHandler {

    private static final Logger logger = Logger.getLogger(XiaoPAuthenticationHandler.class);

    @Autowired
    private UserManageService userManageService;

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    @Override
    public boolean authenticate(Credentials credentials) throws AuthenticationException {
        if (logger.isDebugEnabled()) {
            logger.debug("Current authenticated credentials is " + credentials);
        }
        String username = "";
        try {
            PCredentials pCredentials = (PCredentials) credentials;
            String token = pCredentials.getToken();
            username = userManageService.getUsernameByXiaoP(token);
        } catch (Exception e) {
            logger.info("XiaoPauthentication error: username");
            return false;
        }
        if (!"".equals(username))
            return true;
        return false;
    }

    @Override
    public boolean supports(Credentials credentials) {
        return true;
    }

}
