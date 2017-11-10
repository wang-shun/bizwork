package com.sogou.bizwork.cas.authentication;

import org.springframework.ldap.core.AuthenticationSource;

public class BizworkSecurityAuthenticationSource implements AuthenticationSource {

    private static ThreadLocal<String> userName = new ThreadLocal<String>();
    private static ThreadLocal<String> password = new ThreadLocal<String>();

    public String getPrincipal() {
        return userName.get();
    }

    public String getCredentials() {
        return password.get();
    }

    public static void setUserName(String userName) {
        BizworkSecurityAuthenticationSource.userName.set(userName);
    }

    public static void setPassword(String password) {
        BizworkSecurityAuthenticationSource.password.set(password);
    }

}
