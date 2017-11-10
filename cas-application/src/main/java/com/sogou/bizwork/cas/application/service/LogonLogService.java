package com.sogou.bizwork.cas.application.service;

import java.util.Map;


import com.sogou.bizwork.cas.application.dto.LogonLogDto;
import com.sogou.bizwork.cas.application.dto.Page;
import com.sogou.bizwork.cas.application.model.LogonLog;



public interface LogonLogService {

	public abstract void createLogonLog(Long accountid, LogonLogDto logonLogDto);

	public abstract LogonLog queryLogonLogLastByAccountid(Long accountid);
	
	public abstract LogonLog queryLogonLogLastByUserAndType(Long accountid,Integer userType);

	public Map queryLogonLogLastByAccountid(Long accountid,Page page ,String startdate ,String enddate);

}