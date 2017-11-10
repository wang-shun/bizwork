package com.sogou.bizwork.cas.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ErrorCode {
	
	private static final Logger logger = Logger.getLogger(ErrorCode.class);
	
	private static Map<Integer, String> msgMap = new HashMap<Integer, String>();
	
	private static void addToMsgMap(Integer code, String msg) {
		if(null != msgMap.get(code)) {
			throw new RuntimeException("code already existed in msgMap. code:" + code);
		}
		logger.info("load errorCode:" + code + " msg:" + msg);

		msgMap.put(code, msg);
	}

	public static String getMsg(Integer code) {
		return msgMap.get(code);
	}
	
	/********UC ***/
	
	public static final Integer UC_QUERY_ERROR = 110000;
	static{addToMsgMap(UC_QUERY_ERROR, "获取用户信息失败，请稍后重试");}
	
	public static final Integer UC_UPDATE_ERROR = 110001;
	static{addToMsgMap(UC_UPDATE_ERROR, "更新用户信息失败，请稍后重试");}
	
	public static final Integer UC_VALIDATION_ERROR = 110002;
	static{addToMsgMap(UC_VALIDATION_ERROR, "用户不存在或密码错误");}
}
