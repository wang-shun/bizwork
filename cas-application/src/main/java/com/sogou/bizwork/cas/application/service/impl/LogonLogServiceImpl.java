package com.sogou.bizwork.cas.application.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.log4j.Logger;
import org.omg.CORBA.TIMEOUT;
import org.quartz.Calendar;

import com.sogou.bizwork.cas.application.dao.LogonLogMapper;
import com.sogou.bizwork.cas.application.dto.LogonLogDto;
import com.sogou.bizwork.cas.application.dto.Page;
import com.sogou.bizwork.cas.application.model.LogonLog;
import com.sogou.bizwork.cas.application.service.LogonLogService;


/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 下午08:49:11
 * 
 */
public class LogonLogServiceImpl implements LogonLogService {
	private LogonLogMapper logonLogMapper;
	
	public void createLogonLog(Long accountid, LogonLogDto logonLogDto) {
		LogonLog logonLog = new LogonLog();
		logonLog.setAccountId(accountid.intValue());
		logonLog.setUserIp(logonLogDto.getUserip());
		logonLog.setServiceUrl(logonLogDto.getServiceurl());
		logonLog.setAccountName(logonLogDto.getAccountname());
		logonLog.setUserType(logonLogDto.getUserType());
		logonLog.setStatus(0);
		logonLog.setLogonTime(new Date());
		logonLogMapper.insert(logonLog);

	}

	
	public LogonLog queryLogonLogLastByAccountid(Long accountid) {
		//TODO
		return null;
	}

	
	public LogonLog queryLogonLogLastByUserAndType(Long accountid,
			Integer userType) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Map queryLogonLogLastByAccountid(Long accountid, Page page,
			String startdate, String enddate) {
		// TODO Auto-generated method stub
		return null;
	}

	public LogonLogMapper getLogonLogMapper() {
		return logonLogMapper;
	}

	public void setLogonLogMapper(LogonLogMapper logonLogMapper) {
		this.logonLogMapper = logonLogMapper;
	}
	
	

}
