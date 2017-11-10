package com.sogou.bizwork.cas.logonLog;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sogou.bizwork.cas.application.dto.LogonLogDto;
import com.sogou.bizwork.cas.application.service.LogonLogService;
import com.sogou.bizwork.cas.constants.SecurityConstants;
import com.sogou.bizwork.cas.user.service.UserManageService;


public class LogonLogThreadHandler {
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SecurityConstants.MAIL_POOL_COREPOOLSIZE, 
			SecurityConstants.MAIL_POOL_MAXPOOLSIZE, SecurityConstants.MAIL_POOL_KEEPALIVETIME,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(SecurityConstants.MAIL_POOL_WORKQUEUESIZE),  
			new ThreadPoolExecutor.CallerRunsPolicy());
	
	public static void updateAccountLogonTime(UserManageService userManageService,Long accountId){
		pool.execute(new UpdateLogonTimeThread(userManageService,accountId));
	}
	
	public static void createLogonLog(LogonLogService logonLogService,Long accountid,String password, LogonLogDto logonLogDto){
		pool.execute(new LogonLogThread(logonLogService, accountid, password,logonLogDto));
	}
	
}
