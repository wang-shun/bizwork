package com.sogou.bizwork.cas.constants;

import org.apache.log4j.Logger;

import com.github.inspektr.common.web.ClientInfo;
import com.github.inspektr.common.web.ClientInfoHolder;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午02:49:08
 * 
 */
public class AuthenticationLogHelper implements SSOLogConstantsCode {

	private static final int DEFAULT_SIZE = 1024;

	public static final String AUTHENTICATION_LOG_NAME = "com.sogou.bizwork.cas.AuthenticationLog";
	
	private static final Logger userAuthenticationLogger = Logger.getLogger(AUTHENTICATION_LOG_NAME);

	/**
	 * Log user authentication status
	 *
	 * @param result
	 * @param resultType
	 * @param service
	 * @param sessionId
	 * @param accountId
	 * @param email
	 * @param userStatus
	 * @param description
	 */
	public final static void userAuthenticationLog(String result,
			String resultType, String service, String sessionId,
			Long accountId, String email, String userStatus, String description) {
		String log = getLogRecord(result, resultType, service, sessionId,
				accountId, email, userStatus, description);
		userAuthenticationLogger.info(log);
	}

	/**
	 * Log service authentication end.
	 *
	 * @param result
	 * @param resultType
	 * @param service
	 * @param sessionId
	 * @param accountId
	 * @param email
	 * @param userStatus
	 * @param description
	 */
	public final static void serviceAuthenticationEnd(String result,
			String resultType, String service, String sessionId,
			Long accountId, String email, String userStatus, String description) {
		String log = getLogRecord(result, resultType, service, sessionId,
				accountId, email, userStatus, description);
		userAuthenticationLogger.info(log);
	}

	/**
	 * Log authentication begin.
	 *
	 * @param result
	 * @param resultType
	 * @param service
	 * @param sessionId
	 * @param accountId
	 * @param email
	 * @param userStatus
	 * @param description
	 */
	public final static void serviceAuthenticationBegin(String result,
			String resultType, String service, String sessionId,
			Long accountId, String email, String userStatus, String description) {
		String log = getLogRecord(SERVICE_LOGIN_BEGIN, SERVICE_LOGIN_BEGIN,
				service, sessionId, accountId, email, userStatus, description);
		userAuthenticationLogger.info(log);
	}
	
	
	public final static void appAuthenticationLog(String result,
			String resultType, String service, String sessionId,
			Long accountId, String email, String userStatus, String description, String clientIP) {
		String log = getAppLogRecord(result, resultType, service, sessionId,
				accountId, email, userStatus, description, clientIP);
		
		userAuthenticationLogger.info(log);
	}

	/**
	 * Return a log record according to these parameters.
	 *
	 * @param result
	 * @param resultType
	 * @param service
	 * @param sessionId
	 * @param accountId
	 * @param email
	 * @param userStatus
	 * @param description
	 * @return
	 */
	private static String getLogRecord(String result, String resultType,
			String service, String sessionId, Long accountId, String email,
			String userStatus, String description) {
		StringBuilder sb = new StringBuilder(DEFAULT_SIZE);
		ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
		sb.append("[").append(result).append("][").append(resultType)
				.append("][");
		sb.append(clientInfo != null ? clientInfo.getClientIpAddress() : "")
				.append("][")
				.append(clientInfo != null ? clientInfo.getServerIpAddress()
						: "").append("][");
		sb.append(service).append("][").append(sessionId).append("][");
		sb.append(accountId != null ? accountId : "").append("][")
				.append(email).append("][");
		sb.append(userStatus != null ? userStatus : "").append("][")
				.append(description).append("]");
		return sb.toString();
	}
	
	
	private static String getAppLogRecord(String result, String resultType,
			String service, String sessionId, Long accountId, String email,
			String userStatus, String description,String clientIP) {
		StringBuilder sb = new StringBuilder(DEFAULT_SIZE);
		ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
		sb.append("[").append(result).append("][").append(resultType)
				.append("][");
		sb.append(clientIP != null ? clientIP : "")
				.append("][")
				.append(clientInfo != null ? clientInfo.getServerIpAddress()
						: "").append("][");
		sb.append(service).append("][").append(sessionId).append("][");
		sb.append(accountId != null ? accountId : "").append("][")
				.append(email).append("][");
		sb.append(userStatus != null ? userStatus : "").append("][")
				.append(description).append("]");
		return sb.toString();
	}

}
