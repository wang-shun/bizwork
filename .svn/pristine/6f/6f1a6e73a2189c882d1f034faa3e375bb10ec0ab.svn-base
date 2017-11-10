package com.sogou.bizwork.client.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午12:06:31
 * 类说明
 */
public class CommonUtils {
	
    /** Instance of Commons loggerging. */
	protected static final Logger logger = Logger.getLogger(CommonUtils.class);
    /**
     * Check whether the object is null or not. If it is, throw an exception and
     * display the message.
     *
     * @param object  the object to check.
     * @param message the message to display if the object is null.
     */
    public static void assertNotNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    /**
     * Determines if the String is not empty. A string is not empty if it is not
     * null and has a length > 0.
     *
     * @param string the string to check
     * @return true if it is not empty, false otherwise.
     */
    public static boolean isNotEmpty(final String string) {
        return !isEmpty(string);
    }
    
    /**
     * Determines whether the String is null or of length 0.
     *
     * @param string the string to check
     * @return true if its null or length of 0, false otherwise.
     */
    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }
    
    /**
     * Assert that the statement is true, otherwise throw an exception with the
     * provided message.
     *
     * @param cond    the codition to assert is true.
     * @param message the message to display if the condition is not true.
     */
    public static void assertTrue(final boolean cond, final String message) {
        if (!cond) {
            throw new IllegalArgumentException(message);
        }
    }
 
    /**
     * Determines if a String is blank or not. A String is blank if its empty or
     * if it only contains spaces.
     *
     * @param string the string to check
     * @return true if its blank, false otherwise.
     */
    public static boolean isBlank(final String string) {
        return isEmpty(string) || string.trim().length() == 0;
    }
    
    /**
     * Constructs a service url from the HttpServletRequest or from the given
     * serviceUrl. Prefers the serviceUrl provided if both a serviceUrl and a
     * serviceName.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @param service the configured service url (this will be used if not null)
     * @param serverName the server name to  use to constuct the service url if the service param is empty
     * @param artifactParameterName the artifact parameter name to remove (i.e. ticket)
     * @param encode whether to encode the url or not (i.e. Jsession).    
     * @return the service url to use.
     */
    public static String constructServiceUrl(final HttpServletRequest request,
                                               final HttpServletResponse response, final String service, final String serverName, final String artifactParameterName, final boolean encode) {
        if (CommonUtils.isNotBlank(service)) {
            return encode ? response.encodeURL(service) : service;
        }

        final StringBuilder buffer = new StringBuilder();


        if (!serverName.startsWith("https://") && !serverName.startsWith("http://")) {
            buffer.append(request.isSecure() ? "https://" : "http://");
        }
       	buffer.append(serverName);
        buffer.append(request.getRequestURI());
        if (CommonUtils.isNotBlank(request.getQueryString())) {
            final int location = request.getQueryString().indexOf(artifactParameterName + "=");
            if (location == 0) {
                final String returnValue = encode ? response.encodeURL(buffer.toString()): buffer.toString();
                logger.info("serviceUrl generated: " + returnValue);
                return returnValue;
            }
            buffer.append("?");
            if (location == -1) {
                buffer.append(request.getQueryString());
            } else if (location > 0) {
                final int actualLocation = request.getQueryString()
                        .indexOf("&" + artifactParameterName + "=");

                if (actualLocation == -1) {
                    buffer.append(request.getQueryString());
                } else if (actualLocation > 0) {
                    buffer.append(request.getQueryString().substring(0,
                            actualLocation));
                }
            }
        }

        final String returnValue = encode ? response.encodeURL(buffer.toString()) : buffer.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("serviceUrl generated: " + returnValue);
        }
        return returnValue;
    }
    
    
    /**
     * Determines if a string is not blank. A string is not blank if it contains
     * at least one non-whitespace character.
     *
     * @param string the string to check.
     * @return true if its not blank, false otherwise.
     */
    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }
    
    
    /**
     * Safe method for retrieving a parameter from the request without disrupting the reader UNLESS the parameter
     * actually exists in the query string.
     * <p>
     * Note, this does not work for POST Requests for "loggeroutRequest".  It works for all other CAS POST requests because the
     * parameter is ALWAYS in the GET request.
     * <p>
     * If we see the "loggeroutRequest" parameter we MUST treat it as if calling the standard request.getParameter.
     *
     * @param request the request to check.
     * @param parameter the parameter to look for.
     * @return the value of the parameter.
     */
    public static String safeGetParameter(final HttpServletRequest request, final String parameter) {
        if ("POST".equals(request.getMethod()) && "loggeroutRequest".equals(parameter)) {
            logger.debug("safeGetParameter called on a POST HttpServletRequest for loggeroutRequest.  Cannot complete check safely.  Reverting to standard behavior for this Parameter");
            return request.getParameter(parameter);
        }
        return request.getQueryString() == null || request.getQueryString().indexOf(parameter) == -1 ? null : request.getParameter(parameter);       
    }

}
