package com.sogou.bizwork.cas.application.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jasig.cas.services.RegisteredService;

import com.sogou.bizwork.cas.application.dao.AbstractServiceRegistryDao;
import com.sogou.bizwork.cas.application.dao.ApplicationMapper;
import com.sogou.bizwork.cas.application.model.Application;
import com.sogou.bizwork.cas.application.model.ApplicationExample;
import com.sogou.bizwork.cas.application.service.ApplicationService;
import com.sogou.bizwork.cas.application.service.BizworkApplicationService;

public class ServiceRegistryImpl implements AbstractServiceRegistryDao {

	 private ApplicationMapper applicationMapper;
	
	public void setApplicationMapper(ApplicationMapper applicationMapper) {
		this.applicationMapper = applicationMapper;
	}
	private BizworkApplicationService bizworkApplicationService;
	
	public RegisteredService save(RegisteredService registeredService) {
		long id = registeredService.getId();
		Application application = null;
		if (id>0) {
			application = bizworkApplicationService.selectApplicationByPrimaryKey(id);
		}
		if (null==application) {
			//Insert
			Application app = new Application();
			app.setServiceId(registeredService.getServiceId());
			app.setIntraneturl(registeredService.getServiceId());
			app.setName(registeredService.getName());
			app.setSsoEnabled(registeredService.isSsoEnabled()? 1:0);
			app.setDescription(registeredService.getDescription());
			Date date = new Date();
			app.setChangeDate(date);
			app.setCreateDate(date);
			bizworkApplicationService.createApplication(app);
			return new ApplicationService(app);
		} else {
			//Update
			Application app = new Application();
			app.setAppId(new Long(registeredService.getId()).intValue());
			app.setServiceId(registeredService.getServiceId());
			app.setIntraneturl(registeredService.getServiceId());
			app.setName(registeredService.getName());
			app.setSsoEnabled(registeredService.isSsoEnabled()? 1:0);
			app.setDescription(registeredService.getDescription());
			Date date = new Date();
			app.setChangeDate(date);
			app.setCreateDate(date);
			bizworkApplicationService.updateApplicationByAppId(app);
			return new ApplicationService(app);
		}
	}

	
	public boolean delete(RegisteredService registeredService) {
		return bizworkApplicationService.deleteApplicationByAppId(registeredService.getId())==1;
	}

	
	public List<RegisteredService> load() {
		List<Application> apps = applicationMapper.selectByExample(new ApplicationExample());
		List<RegisteredService> result = new ArrayList<RegisteredService>(apps.size());
		for (Iterator<Application> iterator = apps.iterator(); iterator.hasNext();) {
			Application app = iterator.next();
			result.add(new ApplicationService(app));
		}
		return result;
	}

	
	public RegisteredService findServiceById(long id) {
		Application app = bizworkApplicationService.selectApplicationByPrimaryKey(id);
		return null!=app? new ApplicationService(app):null;
	}

	
	public RegisteredService findServiceByServiceId(String servicename) {
		Application app = bizworkApplicationService.seleApplicationByAppId(servicename);
		return null!=app? new ApplicationService(app):null;
	}

	public BizworkApplicationService getBizworkApplicationService() {
		return bizworkApplicationService;
	}

	public void setBizworkApplicationService(
			BizworkApplicationService bizworkApplicationService) {
		this.bizworkApplicationService = bizworkApplicationService;
	}

	public ApplicationMapper getApplicationMapper() {
		return applicationMapper;
	}
}