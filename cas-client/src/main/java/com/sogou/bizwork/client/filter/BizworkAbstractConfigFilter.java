package com.sogou.bizwork.client.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.validation.Assertion;

import com.sogou.bizwork.client.logger.AuthenticationLogger;
import com.sogou.bizwork.client.logger.DefaultAuthenticationLogger;
import com.sogou.bizwork.client.utils.CommonUtils;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 上午11:11:42
 * 类说明
 */
public class BizworkAbstractConfigFilter extends AbstractConfigurationFilter{
	
	protected static final Logger logger = Logger.getLogger(AbstractConfigurationFilter.class);
	
	public static final String CONFIG_USE_SSO = "useSSO";
	
	public static final String CONFIG_USE_BIZWORK = "bizworkConfig";
	
	/** Represents the constant for where the assertion will be located in memory. */
    public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";

    /** Defines the parameter to look for for the artifact. */
    protected String artifactParameterName = "ticket";

    /** Defines the parameter to look for for the service. */
    protected String serviceParameterName = "service";
    
    /** Sets where response.encodeUrl should be called on service urls when constructed. */
    protected boolean encodeServiceUrl = true;

    /**
     * The name of the server.  Should be in the following format: {protocol}:{hostName}:{port}.
     * Standard ports can be excluded. */
    protected  static String serverName;

    /** The exact url of the service. */
    protected String service;
    
    /**
	 * The URL to the CAS Server login.
	 */
    protected static String casServerLoginUrl;

	/**
	 * Whether to send the renew request or not.
	 */
	protected static boolean renew = false;

	/**
	 * Whether to send the gateway request or not.
	 */
	protected boolean gateway = false;

	public static final String CONFIG_NONCE_NAME = "nonceName";

	public static final String DEFAULT_NONCE_NAME = "nonce";

	private static final String DEFAULT_LOGINURL = "http://bizwork.sogou-inc.com/login";

	private static final String DEFAULT_PREFIX = "http://bizwork.sogou-inc.com";

	protected static String nonceName = null;
	
	protected AuthenticationLogger authenticationLogger = null;
	
	protected String bizworkConfig;
	
	protected static boolean useSSO;
	
	protected static String casServerUrlPrefix;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            setServerName(getPropertyFromInitParams(filterConfig, "serverName", null));
            logger.trace("Loading serverName property: " + this.serverName);
            setService(getPropertyFromInitParams(filterConfig, "service", null));
            logger.trace("Loading service property: " + this.service);
            setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
            logger.trace("Loading artifact parameter name property: " + this.artifactParameterName);
            setServiceParameterName(getPropertyFromInitParams(filterConfig, "serviceParameterName", "service"));
            logger.trace("Loading serviceParameterName property: " + this.serviceParameterName);
            setEncodeServiceUrl(parseBoolean(getPropertyFromInitParams(filterConfig, "encodeServiceUrl", "true")));
            logger.trace("Loading encodeServiceUrl property: " + this.encodeServiceUrl);
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig,
					"casServerLoginUrl", null));
			setRenew(false);
			setGateway(false);
			setNonceName(getPropertyFromInitParams(filterConfig,
					CONFIG_NONCE_NAME, DEFAULT_NONCE_NAME));
			final String gatewayStorageClass = getPropertyFromInitParams(
					filterConfig, "gatewayStorageClass", null);
			setCasServerUrlPrefix(getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null));
			
			final String authenticationLoggerClass = getPropertyFromInitParams(
					filterConfig, "authenticationLoggerClass", null);
			if (authenticationLoggerClass != null) {
				try {
					this.authenticationLogger = (AuthenticationLogger) Class.forName(
							authenticationLoggerClass).newInstance();
				} catch (final Exception e) {
					logger.error("Exception:" + e.getMessage(), e);
					throw new ServletException(e);
				}
			} else {
				this.authenticationLogger = new DefaultAuthenticationLogger();
			}
			String useSSOStr = getPropertyFromInitParams(filterConfig, CONFIG_USE_SSO, "true");
			useSSO =  parseBoolean(useSSOStr);
        }
        
        if(null == casServerLoginUrl || null == serverName ){
        	logger.info("Load Eunomia-Client parameters from JNDI is Empty, Try Reload from Properties");
        	
        	this.bizworkConfig = (String) filterConfig.getServletContext().getAttribute(CONFIG_USE_BIZWORK);
        	initProperties();
        }
         
        logger.info("Loader Bizwork-Client Info:serverName="+serverName+"\t casServerLoginUrl="+casServerLoginUrl+"\t casServerUrlPrefix="+casServerUrlPrefix);
        init();
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * Note that trailing slashes should not be used in the serverName.  As a convenience for this common misconfiguration, we strip them from the provided
     * value.
     *
     * @param serverName the serverName. If this method is called, this should not be null.  This AND service should not be both configured.
     */
    public final void setServerName(final String serverName) {
        if (serverName != null && serverName.endsWith("/")) {
            this.serverName = serverName.substring(0, serverName.length()-1);
            logger.info(String.format("Eliminated extra slash from serverName [%s].  It is now [%s]", serverName, this.serverName));
        } else {
            this.serverName = serverName;
        }
    }
    public final void setService(final String service) {
        this.service = service;
    }
    public final void setArtifactParameterName(final String artifactParameterName) {
        this.artifactParameterName = artifactParameterName;
    }
    public final void setServiceParameterName(final String serviceParameterName) {
        this.serviceParameterName = serviceParameterName;
    }
    public final void setEncodeServiceUrl(final boolean encodeServiceUrl) {
    	this.encodeServiceUrl = encodeServiceUrl;
    }
	public final void setCasServerLoginUrl(final String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}
    public final void setRenew(final boolean renew) {
		this.renew = renew;
	}

	public final void setGateway(final boolean gateway) {
		this.gateway = gateway;
	}
	public void setNonceName(String nonceName) {
		this.nonceName = nonceName;
	}

	public static String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public static void setCasServerUrlPrefix(String casServerUrlPrefix) {
		BizworkAbstractConfigFilter.casServerUrlPrefix = casServerUrlPrefix;
	}
	
	private void initProperties() {
		if(null != bizworkConfig || !bizworkConfig.isEmpty()){
			if(!bizworkConfig.startsWith("/")){
				bizworkConfig = "/".concat(bizworkConfig);
			}
			Properties p = new Properties();
			InputStream stream = null;
			try {
				stream = BizworkAbstractConfigFilter.class.getResourceAsStream(bizworkConfig);
				if(null != stream){
					logger.info("Loading Bizwork-Client Properties File "+ bizworkConfig);
					p.load(stream);
				}else{
					throw new RuntimeException("Bizwork-Client Properties Load Error or Not Found,Please Check it");
				}
				try {
					this.serverName = p.getProperty("serverName");
					this.casServerLoginUrl = p.getProperty("bizworkServerLoginUrl")!=null?p.getProperty("bizworkServerLoginUrl"):DEFAULT_LOGINURL;
					this.useSSO = (p.getProperty(CONFIG_USE_SSO) != null) && p.getProperty(CONFIG_USE_SSO).equalsIgnoreCase("true");
					this.casServerUrlPrefix = p.getProperty("bizworkServerUrlPrefix")!=null?p.getProperty("bizworkServerUrlPrefix"):DEFAULT_PREFIX;
					this.nonceName=p.getProperty(CONFIG_NONCE_NAME)!=null?p.getProperty(CONFIG_NONCE_NAME):DEFAULT_NONCE_NAME;
				} catch (NumberFormatException ne) {
					throw new Exception(ne);				}
				 
			} catch (Throwable t) {
				logger.error("Load Eunomia-Client Properties Load Exception",t);
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
	
    /**
     * Initialization method.  Called by Filter's init method or by Spring.  Similar in concept to the InitializingBean interface's
     * afterPropertiesSet();
     */
    public void init() {
        CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
        CommonUtils.assertNotNull(this.serviceParameterName, "serviceParameterName cannot be null.");
        CommonUtils.assertTrue(CommonUtils.isNotEmpty(this.serverName) || CommonUtils.isNotEmpty(this.service), "serverName or service must be set.");
        CommonUtils.assertTrue(CommonUtils.isBlank(this.serverName) || CommonUtils.isBlank(this.service), "serverName and service cannot both be set.  You MUST ONLY set one.");
        CommonUtils.assertNotNull(this.casServerLoginUrl,"casServerLoginUrl cannot be null.");
		CommonUtils.assertNotNull(this.authenticationLogger,"authenticationLogger cannot be null.");
    }
    
    
    protected final String constructServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
        return CommonUtils.constructServiceUrl(request, response, this.service, this.serverName, this.artifactParameterName, this.encodeServiceUrl);
    }
    
    public final String getArtifactParameterName() {
        return this.artifactParameterName;
    }

}
