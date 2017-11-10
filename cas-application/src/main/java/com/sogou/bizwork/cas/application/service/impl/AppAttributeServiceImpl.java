package com.sogou.bizwork.cas.application.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.sogou.bizwork.cas.application.dao.AppAttributeMapper;
import com.sogou.bizwork.cas.application.model.AppAttribute;
import com.sogou.bizwork.cas.application.model.AppAttributeExample;
import com.sogou.bizwork.cas.application.service.AppAttributeService;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 下午04:17:01
 * 
 */
public class AppAttributeServiceImpl implements AppAttributeService {
	@Resource
	private AppAttributeMapper appAttributeMapper;
	
	
	public AppAttribute getAppAttributeById(Integer appId,
			Integer appattributeId) {
		
		return appAttributeMapper.selectByPrimaryKey(appattributeId.intValue());
	}

	
	public void saveAppAttribute(Integer appId, AppAttribute appAttribute) {
		appAttributeMapper.insert(appAttribute);

	}

	
	public void updateAppAttribute(Integer appId, AppAttribute appAttribute) {
		appAttributeMapper.updateByPrimaryKey(appAttribute);

	}

	
	public void deleteAppAttributeById(Integer appId, Integer appattributeId) {
		AppAttributeExample example = new AppAttributeExample();
		example.createCriteria().andAppIdEqualTo(appId.intValue());
		appAttributeMapper.deleteByExample(example);

	}

	
	public List<AppAttribute> getAllAttributeByAppId(Integer appId) {
		AppAttributeExample example = new AppAttributeExample();
		example.createCriteria().andAppIdEqualTo(appId.intValue());
		return appAttributeMapper.selectByExample(example);
	}

	
	public void deleteAppAttributeByAppId(Integer appId) {
		AppAttributeExample example = new AppAttributeExample();
		example.createCriteria().andAppIdEqualTo(appId.intValue());
		appAttributeMapper.deleteByExample(example);

	}

	public AppAttributeMapper getAppAttributeMapper() {
		return appAttributeMapper;
	}

	public void setAppAttributeMapper(AppAttributeMapper appAttributeMapper) {
		this.appAttributeMapper = appAttributeMapper;
	}
	
	

}
