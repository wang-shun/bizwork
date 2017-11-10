package com.sogou.bizwork.cas.application.service.impl;

import com.sogou.bizwork.cas.application.service.BizworkAuthorizationApplicationService;


public class BizWorkAuthorizationApplicationServiceImpl implements
BizworkAuthorizationApplicationService {

	
	public Short queryLogonServiceTypeByTgt(String tgtId) {
		return null;
	}

	
	public Short updateLogonServiceTypeByTgt(String tgtId,Short userType) {
		return userType;
	}

	
	public void clearLogonServiceTypeByTgt(String tgtId) {
		
	}

}
