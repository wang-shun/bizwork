package com.sogou.bizwork.cas.logonLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogou.bizwork.cas.application.dto.LogonLogDto;
import com.sogou.bizwork.cas.application.service.LogonLogService;



public class LogonLogThread implements Runnable {
	
	Logger logger = LoggerFactory.getLogger(LogonLogThread.class);
	
	private LogonLogService logonLogService;
	private LogonLogDto logonLogDto;
	private Long accountid;
	private String password;
	public LogonLogThread(){
		
	}
	public LogonLogThread(LogonLogService logonLogService , LogonLogDto logonLogDto){
		this.logonLogService = logonLogService;
		this.logonLogDto = logonLogDto;
	}
	public LogonLogThread(LogonLogService logonLogService ,Long accountid,String password, LogonLogDto logonLogDto){
		this.logonLogService = logonLogService;
		this.logonLogDto = logonLogDto;
		this.accountid = accountid;
		this.password = password;
	}
	@Override
	public void run() {
		logonLogService.createLogonLog(accountid, logonLogDto);
	}

}
