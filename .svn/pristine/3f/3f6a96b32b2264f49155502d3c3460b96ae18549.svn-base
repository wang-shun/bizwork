package com.sogou.bizwork.cas.authentication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.AbstractAuthenticationManager;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.handler.BadCredentialsAuthenticationException;
import org.jasig.cas.authentication.handler.UnsupportedCredentialsException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.CredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Principal;

import com.github.inspektr.common.web.ClientInfoHolder;
import com.sogou.bizwork.cas.constants.SSOConstantsCode;
import com.sogou.bizwork.cas.constants.SSOLogConstantsCode;
import com.sogou.bizwork.cas.constants.ThreadLocalHolder;


/**
 * Have to rewrite <code>org.jasig.cas.authentication.AuthenticationManagerImpl</code>  in order to remove <code>org.perf4j.LoggingStopWatch</code>
 * 
 * @author liujianBJ7368
 * @since 2012-06-14
 */
public class BizworkAuthenticationManagerImpl extends AbstractAuthenticationManager implements SSOConstantsCode,SSOLogConstantsCode {
    /** An array of authentication handlers. */
    //@NotNull
    //@Size(min=1)
    private List<AuthenticationHandler> authenticationHandlers;

    /** An array of CredentialsToPrincipalResolvers. */
    //@NotNull
    //@Size(min=1)
    private List<CredentialsToPrincipalResolver> credentialsToPrincipalResolvers;

    protected final Logger logger = Logger.getLogger(BizworkAuthenticationManagerImpl.class);
    @Override
    protected Pair<AuthenticationHandler, Principal> authenticateAndObtainPrincipal(final Credentials credentials) throws AuthenticationException {
        boolean foundSupported = false;
        boolean authenticated = false;
        AuthenticationHandler authenticatedClass = null;
        
        for (final AuthenticationHandler authenticationHandler : this.authenticationHandlers) {
            if (authenticationHandler.supports(credentials)) {
                foundSupported = true;
                boolean  auth = authenticationHandler.authenticate(credentials);
                if (!auth) {
                        logger.info("AuthenticationHandler: "
                                + authenticationHandler.getClass().getName()
                                + " failed to authenticate the user which provided the following credentials: "
                                + credentials.toString());
                  
                } else {
                    if (logger.isInfoEnabled()) {
                        logger.info("AuthenticationHandler: "
                                + authenticationHandler.getClass().getName()
                                + " successfully authenticated the user which provided the following credentials: "
                                + credentials.toString());
                    }
                    authenticatedClass = authenticationHandler;
                    authenticated = true;
                    break;
                }
            }
        }
        if (!authenticated) {
            throw UnsupportedCredentialsException.ERROR;
        }
        foundSupported = false;

        for (final CredentialsToPrincipalResolver credentialsToPrincipalResolver : this.credentialsToPrincipalResolvers) {
            if (credentialsToPrincipalResolver.supports(credentials)) {
                final Principal principal = credentialsToPrincipalResolver.resolvePrincipal(credentials);
                if (logger.isDebugEnabled()) {
                	logger.debug("Resolved principal " + principal);
                }
                foundSupported = true;
                if (principal != null) {
                    return new Pair<AuthenticationHandler,Principal>(authenticatedClass, principal);
                }
            }
        }

        if (foundSupported) {
            if (logger.isDebugEnabled()) {
                logger.debug("CredentialsToPrincipalResolver found but no principal returned.");
            }
            throw BadCredentialsAuthenticationException.ERROR;
        }

        logger.error("CredentialsToPrincipalResolver not found for " + credentials.getClass().getName());
        throw UnsupportedCredentialsException.ERROR;
    }

    private static final String[] AGENT_ROLES = new String[]{"a0","a1","a1t","a2","a2t","a3","a3t"};
    
    private static Set<String> AGENT_ROLES_SET = new HashSet<String>();
    
    static {
    	for (int i = 0; i < AGENT_ROLES.length; i++) {
			String role = AGENT_ROLES[i];
			AGENT_ROLES_SET.add(role);
		}
    }
    
    /**
     * @param authenticationHandlers The authenticationHandlers to set.
     */
    public void setAuthenticationHandlers(
        final List<AuthenticationHandler> authenticationHandlers) {
        this.authenticationHandlers = authenticationHandlers;
    }

    /**
     * @param credentialsToPrincipalResolvers The
     * credentialsToPrincipalResolvers to set.
     */
    public void setCredentialsToPrincipalResolvers(
        final List<CredentialsToPrincipalResolver> credentialsToPrincipalResolvers) {
        this.credentialsToPrincipalResolvers = credentialsToPrincipalResolvers;
    }

	public List<AuthenticationHandler> getAuthenticationHandlers() {
		return authenticationHandlers;
	}

	public List<CredentialsToPrincipalResolver> getCredentialsToPrincipalResolvers() {
		return credentialsToPrincipalResolvers;
	}
    
    
    
}
