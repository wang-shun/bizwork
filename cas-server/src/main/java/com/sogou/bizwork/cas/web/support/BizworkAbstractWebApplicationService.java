package com.sogou.bizwork.cas.web.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SamlService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.util.DefaultUniqueTicketIdGenerator;
import org.jasig.cas.util.HttpClient;
import org.jasig.cas.util.SamlUtils;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation of a WebApplicationService By Eunomia For Support Intranet Logon&Logout.
 * 
 * @author zhanggaozheng
 * 
 * @since 1.6.5.0
 *
 */
public abstract class BizworkAbstractWebApplicationService implements WebApplicationService {

	private static final long serialVersionUID = 807622610445961256L;

	protected static final Logger LOG = LoggerFactory.getLogger(SamlService.class);
    
    private static final Map<String, Object> EMPTY_MAP = Collections.unmodifiableMap(new HashMap<String, Object>());
    
    private static final UniqueTicketIdGenerator GENERATOR = new DefaultUniqueTicketIdGenerator();
    
    /** The id of the service. */
    private final String id;
    
    /** The original url provided, used to reconstruct the redirect url. */
    private final String originalUrl;
    
    /** The intranet url provided, used to reconstruct the Intranet url */
    private final String intranetUrl;

    private final String artifactId;
    
    private Principal principal;
    
    private boolean loggedOutAlready = false;
    
    private final HttpClient httpClient;
    
    protected BizworkAbstractWebApplicationService(final String id, final String originalUrl,final String intranetUrl,
    		final String artifactId, final HttpClient httpClient) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.artifactId = artifactId;
        this.httpClient = httpClient;
        this.intranetUrl = intranetUrl;
    }
    
    
    public final String toString() {
        return this.id;
    }
    
    public final String getId() {
        return this.id;
    }
    
    public final String getArtifactId() {
        return this.artifactId;
    }

    public final Map<String, Object> getAttributes() {
        return EMPTY_MAP;
    }

    protected static String cleanupUrl(final String url) {
        if (url == null) {
            return null;
        }

        final int jsessionPosition = url.indexOf(";jsession");

        if (jsessionPosition == -1) {
            return url;
        }

        final int questionMarkPosition = url.indexOf("?");

        if (questionMarkPosition < jsessionPosition) {
            return url.substring(0, url.indexOf(";jsession"));
        }

        return url.substring(0, jsessionPosition)
            + url.substring(questionMarkPosition);
    }
    
    protected final String getOriginalUrl() {
        return this.originalUrl;
    }

    protected final HttpClient getHttpClient() {
        return this.httpClient;
    }

    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }

        if (object instanceof Service) {
            final Service service = (Service) object;

            return getId().equals(service.getId());
        }

        return false;
    }
    
    public int hashCode() {
        final int prime = 41;
        int result = 1;
        result = prime * result
            + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
    
    protected Principal getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(final Principal principal) {
        this.principal = principal;
    }
    
    public boolean matches(final Service service) {
        return this.id.equals(service.getId());
    }
    
    public synchronized boolean logOutOfService(final String sessionIdentifier) {
        if (this.loggedOutAlready) {
            return true;
        }

        LOG.debug("Sending logout request for: " + getId());

        final String logoutRequest = "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\""
            + GENERATOR.getNewTicketId("LR")
            + "\" Version=\"2.0\" IssueInstant=\"" + SamlUtils.getCurrentDateAndTime()
            + "\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">@NOT_USED@</saml:NameID><samlp:SessionIndex>"
            + sessionIdentifier + "</samlp:SessionIndex></samlp:LogoutRequest>";
        
        this.loggedOutAlready = true;
        
        if (this.httpClient != null) {
            return this.httpClient.sendMessageToEndPoint(getOriginalUrl(), logoutRequest, true);
        }
        
        return false;
    }

	public String getIntranetUrl() {
		return intranetUrl;
	}
}