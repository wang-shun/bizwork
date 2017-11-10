package com.sogou.bizwork.cas.principal;

import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

import com.sogou.bizwork.cas.constants.SSOConstantsCode;
import com.sogou.bizwork.cas.constants.ThreadLocalHolder;
import com.sogou.bizwork.cas.constants.UUCConstants;
import com.sogou.bizwork.cas.user.model.User;
import com.sogou.bizwork.cas.utils.BizworkCredentials;
import com.sogou.bizwork.cas.utils.PCredentials;

public class AbstractCrendentialsToPrincipalResolver implements CredentialsToPrincipalResolver, UUCConstants {

    protected String extractPrincipalId(final Credentials credentials) {
        final UsernamePasswordCredentials usernamePasswordCredentials = (UsernamePasswordCredentials) credentials;
        return usernamePasswordCredentials.getUsername();
    }

    @Override
    public Principal resolvePrincipal(Credentials credentials) {
        User user = null;
        if (User.class.isInstance(ThreadLocalHolder.getProperty(SSOConstantsCode.CURRENT_LOGIN_USER))) {
            user = (User) ThreadLocalHolder.getProperty(SSOConstantsCode.CURRENT_LOGIN_USER);
        }
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (null != user) {
            attributes.put(UUCConstants.USER_ID, user.getId());
        }

        if (PCredentials.class.isInstance(credentials)) {
            // 原基础上新增代码，应对小P认证的情况，如果是小P来认证，走该逻辑
            final PCredentials pCredentials = (PCredentials) credentials;
            Principal principal = new SimplePrincipal(pCredentials.getUsername().trim(), attributes);
            return principal;
        }

        final BizworkCredentials bizworkCredentials = (BizworkCredentials) credentials;
        Principal principal = new SimplePrincipal(bizworkCredentials.getUsername().trim(), attributes);
        return principal;

    }

    @Override
    public boolean supports(Credentials credentials) {
        return true;
    }
}
