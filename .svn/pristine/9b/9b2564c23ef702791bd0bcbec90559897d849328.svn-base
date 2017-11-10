package com.sogou.bizwork.cas.application.service.impl;

import java.util.List;

import com.sogou.bizwork.cas.application.dao.ApplicationMapper;
import com.sogou.bizwork.cas.application.model.Application;
import com.sogou.bizwork.cas.application.model.ApplicationExample;
import com.sogou.bizwork.cas.application.service.BizworkApplicationService;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 下午03:48:14
 * 
 */
public class BizworkApplicationServiceImpl implements BizworkApplicationService {

	private ApplicationMapper applicationMapper;
	
	public List<Application> selectAllApplications() {
		return applicationMapper.selectByExample(new ApplicationExample());
	}

	
	public Application seleApplicationByAppId(String serviceId) {
		ApplicationExample applicationExample = new ApplicationExample();
		applicationExample.createCriteria().andServiceIdEqualTo(serviceId);
		List<Application> applications = applicationMapper.selectByExample(applicationExample);
		if (applications.size()>0) {
			return applications.get(0);
		}
		return null;
	}

	
	public void createApplication(Application application) {
		 applicationMapper.insert(application);

	}

	
	public int updateApplicationByAppId(Application record) {
		return applicationMapper.updateByPrimaryKey(record);
	}

	
	public int deleteApplicationByAppId(Long appId) {
		 return applicationMapper.deleteByPrimaryKey(appId.intValue());
	}

	
	public Application selectApplicationByPrimaryKey(Long appId) {
		return applicationMapper.selectByPrimaryKey(appId.intValue());
	}

	public ApplicationMapper getApplicationMapper() {
		return applicationMapper;
	}

	public void setApplicationMapper(ApplicationMapper applicationMapper) {
		this.applicationMapper = applicationMapper;
	}
}
