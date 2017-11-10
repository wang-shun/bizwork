package com.sogou.bizwork.cas.authentication;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.principal.Credentials;
import org.springframework.beans.factory.annotation.Autowired;

import com.sogou.bizwork.cas.constants.SecurityConstants;
import com.sogou.bizwork.cas.utils.AESForNodejs;
import com.sogou.bizwork.cas.utils.BizworkCredentials;

/**
 * 
 * @author liujianBJ7368
 * @since 2012-05-17
 */
public class LdapAuthenticationHandler implements AuthenticationHandler {

    private static final Logger logger = Logger.getLogger(LdapAuthenticationHandler.class);

    @Autowired
    private LdapService ldapService;

    public LdapService getLdapService() {
        return ldapService;
    }

    public void setLdapService(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @Override
    public boolean authenticate(Credentials credentials) throws AuthenticationException {
        if (logger.isDebugEnabled()) {
            logger.debug("Current authenticated credentials is " + credentials);
        }
        boolean result = false;
        BizworkCredentials bizworkCre = null;
        try {
            bizworkCre = (BizworkCredentials) credentials;
            String dePasswrod = AESForNodejs.decrypt(bizworkCre.getPassword(), SecurityConstants.AES_KEY);
            result = ldapService.authenticate(bizworkCre.getUsername(), dePasswrod);
        } catch (Exception e) {
            logger.error("Ldap authentication error: username" + bizworkCre.getUsername());
        }
        return result;

    }

    @Override
    public boolean supports(Credentials credentials) {
        return credentials.getClass().equals(BizworkCredentials.class);
    }

}
