package com.sogou.bizwork.client.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

import com.sogou.bizwork.client.logger.AuthenticationLogger;
import com.sogou.bizwork.client.logger.DefaultAuthenticationLogger;
import com.sogou.bizwork.client.utils.CommonUtils;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午05:40:40
 * 类说明
 */
public class BizworkAbstractTicketValidationFilter extends BizworkAbstractConfigFilter {
	
	private static final int DEFAULT_MILLIS_BETWEEN_CLEANUPS = 60 * 1000;
	protected int millisBetweenCleanUps;
    /** Determines whether an exception is thrown when there is a ticket validation failure. */
    private boolean exceptionOnValidationFailure = false;
    /**
     * Specify whether the filter should redirect the user agent after a
     * successful validation to remove the ticket parameter from the query
     * string.
     */
    private boolean redirectAfterValidation = false;
    private boolean useSession = true;
	/**
	 * Add "excludePath", "verifyHostname" to be exclued in constructing
	 * validation urls.
	 */
	private static final String[] RESERVED_INIT_PARAMS = new String[] {
			"proxyGrantingTicketStorageClass", "proxyReceptorUrl",
			"acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix",
			"proxyCallbackUrl", "renew", "exceptionOnValidationFailure",
			"redirectAfterValidation", "useSession", "serverName", "service",
			"artifactParameterName", "serviceParameterName",
			"encodeServiceUrl", "millisBetweenCleanUps", "hostnameVerifier",
			"encoding", "config", "excludePath", "verifyHostname" };
    /** The TicketValidator we will use to validate tickets. */
	protected TicketValidator ticketValidator;
	
	protected Timer timer;
	protected TimerTask timerTask;
	
	private static final String DEFAULT_LOGOUT = "https://bizwork.sogou-inc.com/logout";
	
    public void init(final FilterConfig filterConfig) throws ServletException {

		this.millisBetweenCleanUps = Integer.parseInt(getPropertyFromInitParams(filterConfig,
						"millisBetweenCleanUps",
						Integer.toString(DEFAULT_MILLIS_BETWEEN_CLEANUPS)));

		final String authenticationLoggerClass = getPropertyFromInitParams(
				filterConfig, "authenticationLoggerClass", null);
		if (authenticationLoggerClass != null) {
			try {
				this.authenticationLogger = (AuthenticationLogger) Class
						.forName(authenticationLoggerClass).newInstance();
			} catch (final Exception e) {
				logger.error("Exception:" + e.getMessage(), e);
				throw new ServletException(e);
			}
		} else {
			this.authenticationLogger = new DefaultAuthenticationLogger();
		}
        setExceptionOnValidationFailure(parseBoolean(getPropertyFromInitParams(filterConfig, "exceptionOnValidationFailure", "false")));
        logger.trace("Setting exceptionOnValidationFailure parameter: " + this.exceptionOnValidationFailure);
        setRedirectAfterValidation(parseBoolean(getPropertyFromInitParams(filterConfig, "redirectAfterValidation", "true")));
        logger.trace("Setting redirectAfterValidation parameter: " + this.redirectAfterValidation);
        setUseSession(parseBoolean(getPropertyFromInitParams(filterConfig, "useSession", "true")));
        logger.trace("Setting useSession parameter: " + this.useSession);
        setTicketValidator(getTicketValidator(filterConfig));
    }
    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.ticketValidator, "ticketValidator cannot be null.");
    }
    
	public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());

        if (CommonUtils.isNotBlank(ticket)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Attempting to validate ticket: " + ticket);
            }

            try {
                final Assertion assertion = this.ticketValidator.validate(ticket, constructServiceUrl(request, response));

                if (logger.isDebugEnabled()) {
                    logger.debug("Successfully authenticated user: " + assertion.getPrincipal().getName());
                }

                request.setAttribute(CONST_CAS_ASSERTION, assertion);

                if (this.useSession) {
                    request.getSession().setAttribute(CONST_CAS_ASSERTION, assertion);
                }
                onSuccessfulValidation(request, response, assertion);

                if (this.redirectAfterValidation) {
                    logger. debug("Redirecting after successful ticket validation.");
                    response.sendRedirect(constructServiceUrl(request, response));
                    return;
                }
            } catch (final TicketValidationException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                logger.warn(e, e);

                onFailedValidation(request, response);

                if (this.exceptionOnValidationFailure) {
                    throw new ServletException(e);
                }

                return;
            }catch (final Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                logger.warn(e, e);

                onFailedValidation(request, response);

                if (this.exceptionOnValidationFailure) {
                    throw new ServletException(e);
                }

                return;
            }
        }

        filterChain.doFilter(request, response);

    }
    
    public final void setExceptionOnValidationFailure(final boolean exceptionOnValidationFailure) {
        this.exceptionOnValidationFailure = exceptionOnValidationFailure;
    }
    public final void setRedirectAfterValidation(final boolean redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }
    public final void setUseSession(final boolean useSession) {
        this.useSession = useSession;
    }
    
    public final void setTicketValidator(final TicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }
    
    /**
	 * Constructs a Cas20ServiceTicketValidator or a Cas20ProxyTicketValidator
	 * based on supplied parameters.
	 * 
	 * @param filterConfig
	 *            the Filter Configuration object.
	 * @return a fully constructed TicketValidator.
	 */
	protected final TicketValidator getTicketValidator(
			final FilterConfig filterConfig) {
		String casServerUrlPrefix = getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null);
		
		final Cas20ServiceTicketValidator validator;

		if(null ==casServerUrlPrefix){
			casServerUrlPrefix = BizworkAbstractConfigFilter.casServerUrlPrefix;
		}

		validator = new ServiceTicketValidator(casServerUrlPrefix);
		validator.setEncoding(getPropertyFromInitParams(filterConfig,"encoding", null));

		final Map<String, String> additionalParameters = new HashMap<String, String>();
		final List<String> params = Arrays.asList(RESERVED_INIT_PARAMS);

		for (final Enumeration<?> e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
			final String s = (String) e.nextElement();

			if (!params.contains(s)) {
				additionalParameters.put(s, filterConfig.getInitParameter(s));
			}
		}
		additionalParameters.remove(CONFIG_USE_BIZWORK);
		validator.setCustomParameters(additionalParameters);
		validator.setHostnameVerifier(getHostnameVerifier(filterConfig));

		return validator;
	}
	
	
	private class ServiceTicketValidator extends Cas20ServiceTicketValidator {

		public ServiceTicketValidator(String casServerUrlPrefix) {
			super(casServerUrlPrefix);
		}
	     
		public Assertion validate(String ticket, String service)
				throws TicketValidationException {
			String validationUrl = constructValidationUrl(ticket, service);
			if (logger.isDebugEnabled()) {
				logger.debug("Constructing validation url: " + validationUrl);
			}
			try {
				return parseResponseFromBizwork(validationUrl,ticket);
			} catch (MalformedURLException e) {
				throw new TicketValidationException(e);
			}
		}
		
		public  Assertion parseResponseFromBizwork(String validationUrl, String ticket) throws TicketValidationException, MalformedURLException{
			String serverResponse = retrieveResponseFromServer(new URL(validationUrl), ticket);
			if (serverResponse == null) {
				throw new TicketValidationException("The CAS server returned no response.");
			}
			if (logger.isDebugEnabled()) {
				 logger.debug("Server response: " + serverResponse);
			}
			try {
				return parseResponseFromServer(serverResponse);
			} catch (TicketValidationException e) {
				logger.warn("SSO-Client Validate ServiceTicket Failed. Try Once Again!");
				serverResponse = retrieveResponseFromServer(new URL(validationUrl), ticket);
				if (serverResponse == null) {
					throw new TicketValidationException("The CAS server returned no response.");
				}
				if (logger.isDebugEnabled()) {
					 logger.debug("Server response: " + serverResponse);
				}
				try {
					return parseResponseFromServer(serverResponse);
				} catch (Exception re) {
					logger.warn("Ticket Validate  Twice Exception on server response:"+ serverResponse, re);
					throw new TicketValidationException(e);
					
				}
			}
		}
	}
	
    /**
     * Gets the configured {@link HostnameVerifier} to use for HTTPS connections
     * if one is configured for this filter.
     * @param filterConfig Servlet filter configuration.
     * @return Instance of specified host name verifier or null if none specified.
     */
    protected HostnameVerifier getHostnameVerifier(final FilterConfig filterConfig) {
        final String className = getPropertyFromInitParams(filterConfig, "hostnameVerifier", null);
        logger.trace("Using hostnameVerifier parameter: " + className);
        final String config = getPropertyFromInitParams(filterConfig, "hostnameVerifierConfig", null);
        logger.trace("Using hostnameVerifierConfig parameter: " + config);
        if (className != null) {
            if (config != null) {
                return ReflectUtils.newInstance(className, config);
            } else {
                return ReflectUtils.newInstance(className);
            }
        }
        return null;
    }
    
    /**
     * Template method that gets executed if ticket validation succeeds.  Override if you want additional behavior to occur
     * if ticket validation succeeds.  This method is called after all ValidationFilter processing required for a successful authentication
     * occurs.
     *
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     * @param assertion the successful Assertion from the server.
     */
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response, final Assertion assertion) {
        // nothing to do here.                                                                                            
    }
    /**
     * Template method that gets executed if validation fails.  This method is called right after the exception is caught from the ticket validator
     * but before any of the processing of the exception occurs.
     *
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     */
    protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
       String loginUrl = constructLoginUrl(request,response); 
       try {
		response.sendRedirect(loginUrl);
		} catch (Exception e) {
			logger.warn("retry validation token error",e); 
		}
    }

    private String constructLoginUrl(HttpServletRequest request,
			HttpServletResponse response) {
		 return serverName!=null?serverName:DEFAULT_LOGOUT;
	}


}
