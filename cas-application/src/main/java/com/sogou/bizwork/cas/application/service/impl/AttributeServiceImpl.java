package com.sogou.bizwork.cas.application.service.impl;

import java.util.List;
import javax.annotation.Resource;

import com.sogou.bizwork.cas.application.dao.AttributeMapper;
import com.sogou.bizwork.cas.application.model.Attribute;
import com.sogou.bizwork.cas.application.model.AttributeExample;
import com.sogou.bizwork.cas.application.service.AttributeService;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 下午04:13:24
 * 
 */
public class AttributeServiceImpl implements AttributeService {
	@Resource
	private AttributeMapper attributeMapper;
	
	public Attribute getAttributeById(Integer attributeId) {
		return attributeMapper.selectByPrimaryKey(attributeId.intValue());
	}

	
	public Attribute getAttributeByValue(String attributeValue) {
		AttributeExample example = new AttributeExample();
		example.createCriteria().andAttributeValueEqualTo(attributeValue.trim());
		List<Attribute> attributes = attributeMapper.selectByExample(example);
		if(attributes != null && !attributes.isEmpty()){
			return attributes.get(0);
		}
		return null;
	}

	
	public List<Attribute> getAllAttribute() {
		return attributeMapper.selectByExample(new AttributeExample());
	}

	public AttributeMapper getAttributeMapper() {
		return attributeMapper;
	}

	public void setAttributeMapper(AttributeMapper attributeMapper) {
		this.attributeMapper = attributeMapper;
	}
	
}
