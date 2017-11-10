package com.sogou.bizwork.cas.web.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.Response;
import org.jasig.cas.authentication.principal.Response.ResponseType;
import org.jasig.cas.util.DefaultUniqueTicketIdGenerator;
import org.jasig.cas.util.HttpClient;
import org.jasig.cas.util.SamlUtils;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * @author liujianBJ7368
 * @since 2012-05-18
 */
public class BizworkWebApplicationServiceImpl extends BizworkAbstractWebApplicationService {

	protected static final Logger logger = LoggerFactory.getLogger(BizworkWebApplicationServiceImpl.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6163901687735493438L;

	private static final String CONST_PARAM_SERVICE = "service";

    private static final String CONST_PARAM_TARGET_SERVICE = "targetService";

    private static final String CONST_PARAM_TICKET = "ticket";

    private static final String CONST_PARAM_METHOD = "method";

    private final ResponseType responseType;

	private HttpClient httpClient = null;

	private boolean loggedOutAlready = false;

    
    public BizworkWebApplicationServiceImpl(final String id) {
        this(id, id, null,null, null, null);
    }

    public BizworkWebApplicationServiceImpl(final String id, final HttpClient httpClient) {
        this(id, id, null,null, null, httpClient);
        this.httpClient  = httpClient;
    }

    
    private BizworkWebApplicationServiceImpl(final String id,
            final String originalUrl,final String intranetUrl, final String artifactId,
            final ResponseType responseType, final HttpClient httpClient) {
            super(id, originalUrl,intranetUrl, artifactId, httpClient);
            this.responseType = responseType;
            this.httpClient = httpClient;
        }
    
    public static BizworkWebApplicationServiceImpl createServiceFrom(final HttpServletRequest request) {
        return createServiceFrom(request,null, null);
    }

    public static BizworkWebApplicationServiceImpl createServiceFrom(
        final HttpServletRequest request, Map<String, String> extranetToIntranet, final HttpClient httpClient) {
        final String targetService = request
            .getParameter(CONST_PARAM_TARGET_SERVICE);
        final String method = request.getParameter(CONST_PARAM_METHOD);
        final String serviceToUse = StringUtils.hasText(targetService)
            ? targetService : request.getParameter(CONST_PARAM_SERVICE);

        if (!StringUtils.hasText(serviceToUse)) {
            return null;
        }

        final String id = cleanupUrl(serviceToUse);
        final String artifactId = request.getParameter(CONST_PARAM_TICKET);
        
        String intranetUrl = id;
        if(null != extranetToIntranet && !extranetToIntranet.isEmpty()){
        	try{
        		intranetUrl = extranetToIntranet.get(id);
        	}catch(Throwable t){
        		
        	}
        }
        return new BizworkWebApplicationServiceImpl(id, serviceToUse,intranetUrl,
            artifactId, "POST".equals(method) ? ResponseType.POST
                : ResponseType.REDIRECT, httpClient);
    }

    private final static int SERVICE_ID_START_POSITION = "https://".length();
    
    /**
     * Retrieve domain name as service id. 
     * 
     * @param url
     * @return
     */
	protected static String cleanupUrl(String url) {
		if (url == null) {
			return null;
		}
		int loc = url.indexOf('/', SERVICE_ID_START_POSITION);
		
		String result = loc!=-1? url.substring(0, loc):url;
		
		String uri = loc!=-1? url.substring(++loc):url;
		
		if (logger.isDebugEnabled()) {
    		logger.debug("return application url:"+result+" from input url "+url);
    	}
    	return result;
	}
   
    public Response getResponse(final String ticketId) {
        final Map<String, String> parameters = new HashMap<String, String>();

        if (StringUtils.hasText(ticketId)) {
            parameters.put(CONST_PARAM_TICKET, ticketId);
        }

        if (ResponseType.POST == this.responseType) {
            return Response.getPostResponse(getOriginalUrl(), parameters);
        }
        return Response.getRedirectResponse(getOriginalUrl(), parameters);
    }

	@Override
	public Principal getPrincipal() {
		return super.getPrincipal();
	}

	private static final UniqueTicketIdGenerator GENERATOR = new DefaultUniqueTicketIdGenerator();
	
    public synchronized boolean logOutOfService(final String sessionIdentifier) {
        if (this.loggedOutAlready ) {
            return true;
        }
        if (logger.isDebugEnabled()) {
        	logger.debug("Sending logout request for: " + getId() + ", Expired  session identifier is  "+sessionIdentifier);
        }
        final String logoutRequest = "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\""
            + GENERATOR.getNewTicketId("LR")
            + "\" Version=\"2.0\" IssueInstant=\"" + SamlUtils.getCurrentDateAndTime()
            + "\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">@NOT_USED@</saml:NameID><samlp:SessionIndex>"
            + sessionIdentifier + "</samlp:SessionIndex></samlp:LogoutRequest>";
        
        this.loggedOutAlready = true;
        if (logger.isDebugEnabled()) {
        	logger.debug("Sending logout url: " + getIntranetUrl()+"logoutRequest:"+logoutRequest );
        }
        if (this.httpClient != null) {
            return this.httpClient.sendMessageToEndPoint(getIntranetUrl()!=null?getIntranetUrl():getOriginalUrl(), logoutRequest, false);
        }
        
        return false;
    }
    
}