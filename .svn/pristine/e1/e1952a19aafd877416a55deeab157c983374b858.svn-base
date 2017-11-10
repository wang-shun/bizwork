package com.sogou.bizwork.cas.authentication;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.CollectingAuthenticationErrorCallback;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;


/**
 * LDAP认证服务管理
 * @author dongzeguang
 *
 */
@Service
public class LdapServiceImpl implements LdapService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired(required=false)
    private LdapTemplate ldapTemplate;

    @Override
    public boolean authenticate(String email, String password) {
        boolean isAuth = false;
        try {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("userprincipalname", email));
            CollectingAuthenticationErrorCallback errorCallback = new CollectingAuthenticationErrorCallback();

            BizworkSecurityAuthenticationSource.setPassword(password);
            BizworkSecurityAuthenticationSource.setUserName(email);

            isAuth = ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(),
                    password, errorCallback);
            if (isAuth) {
                Exception error = errorCallback.getError();
                if (error != null) {
                    logger.info("[LdapServiceImpl] authenticate by ldap error! user: " + email
                            + ", password: " + password + ", error: " + error.getMessage(), error);
                }
            }
        } catch (Exception e) {
            isAuth = false;
            logger.info(e.getMessage(), e);
        }
        return isAuth;
    }
}
