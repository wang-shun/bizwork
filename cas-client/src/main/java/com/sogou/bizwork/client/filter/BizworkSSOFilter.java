package com.sogou.bizwork.client.filter;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.ssl.AnyHostnameVerifier;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;

import com.sogou.bizwork.client.utils.PathPatternMatcher;
/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 上午11:11:09
 * 类说明
 */
public class BizworkSSOFilter extends BizworkAbstractConfigFilter implements Filter {
	protected Filter[] filters = null;
	
	/**
	 * Determine whether to verify hostname or not.
	 * <p/>
	 * If this configuration parameter set to be <code>false</code>, you do not need 
	 * to install a certificate into the Java CA Cert file. The default value for this 
	 * parameter is <code>true</code>.
	 */
	public static final String CONFIG_VERIFY_HOSTNAME = "verifyHostname"; 
	public static final String CONFIG_EXCLUDE_PATH = "excludePath";

	private static final String DEFAULT_FILE_NAME = "bizworkConfig.properties";
	private List<String> excludePath;
	protected String bizworkConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludePathv = getPropertyFromInitParams(filterConfig, CONFIG_EXCLUDE_PATH, "");

		excludePath = new ArrayList<String>();
		if ( null!=excludePathv && !excludePathv.equals("") && excludePathv.length()>0) {
			String[] paths = excludePathv.split(";");
			excludePath.addAll(Arrays.asList(paths));
		}
		
		bizworkConfig = getPropertyFromInitParams(filterConfig,"bizworkConfig",DEFAULT_FILE_NAME);
		filterConfig.getServletContext().setAttribute("bizworkConfig", bizworkConfig);
		
		BizworkAuthenticationFilter authenticationFilter = new BizworkAuthenticationFilter();
		authenticationFilter.init(filterConfig);
		
		BizworkTicketValidationFilter validationFilter = new BizworkTicketValidationFilter();
		validationFilter.init(filterConfig);
		
		HttpServletRequestWrapperFilter requestWrapperFilter = new HttpServletRequestWrapperFilter();
		requestWrapperFilter.init(filterConfig);
		
		AssertionThreadLocalFilter assertionThreadLocalFilter = new AssertionThreadLocalFilter();
		assertionThreadLocalFilter.init(filterConfig);
		
		List<Filter> filters = new ArrayList<Filter>();
		
		filters.add(authenticationFilter);
		filters.add(validationFilter);
		
		filters.add(requestWrapperFilter);
		filters.add(assertionThreadLocalFilter);
		
		this.filters = filters.toArray(new Filter[]{});
		
		String verifyHostname = getPropertyFromInitParams(filterConfig, CONFIG_VERIFY_HOSTNAME, "true");
				
		if (!parseBoolean(verifyHostname)) {
			HttpsURLConnection.setDefaultHostnameVerifier(new AnyHostnameVerifier());
			HttpsURLConnection.setDefaultSSLSocketFactory(getSSLSocketFactory());
		}
	}
	
	protected SSLSocketFactory getSSLSocketFactory() {
		try {
			final SSLContext sslContext = SSLContext.getInstance("TLSv1");

			sslContext.init(null, new TrustManager[] { new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(
						X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {

				}

				@Override
				public void checkClientTrusted(
						X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}
			} }, new SecureRandom());
			SSLSocketFactory factory = sslContext.getSocketFactory();
			return factory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return whether the SSO/SLO process can be by-passed
	 */
	protected boolean needSSO(ServletRequest request, ServletResponse response) {
		return BizworkAuthenticationFilter.useSSO;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String path = hrequest.getServletPath();
		if("".equals(path) || PathPatternMatcher.urlPathMatch(excludePath, path)) {
			chain.doFilter(request, response);
		} else {
			if (needSSO(request, response)) {
				FilterChain filterChain = createFilterChain(chain);
				filterChain.doFilter(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}
	
	protected FilterChain createFilterChain(FilterChain chain) {
		if (null!=filters) {
			FilterChain lastChain = chain;
			for (int i=filters.length-1; i>=0;i--) {
				FilterChain filterChain = new FilterChainImpl(filters[i], lastChain);
				lastChain = filterChain;
			}
			return lastChain;
		}
		return chain;
	}
	
	private static class FilterChainImpl implements FilterChain {
		
		private Filter filter;
		private final FilterChain chain;

		private FilterChainImpl(Filter filter, FilterChain chain) {
			this.filter = filter;
			this.chain = chain;
		}

		public void doFilter(final ServletRequest request, final ServletResponse response) throws IOException,
				ServletException {
			filter.doFilter(request, response, chain);
		}

	}

	public String getBizworkConfig() {
		return bizworkConfig;
	}

	public void setBizworkConfig(String bizworkConfig) {
		this.bizworkConfig = bizworkConfig;
	}
	
	
	
}
