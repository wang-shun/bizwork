package com.sogou.bizwork.cas.logonLog;

import com.sogou.bizwork.cas.user.service.UserManageService;



public class UpdateLogonTimeThread implements Runnable {

	UserManageService userManageService;
	Long accountId;
	
	public UpdateLogonTimeThread(UserManageService userManageService,
			Long accountId) {
		this.userManageService = userManageService;
		this.accountId = accountId;
	}

	@Override
	public void run() {
		try {
			userManageService.updateLoginTime(accountId);
		} catch (Exception e) {
			 
		}

	}


}
