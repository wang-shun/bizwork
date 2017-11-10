package com.sogou.bizwork.cas.web.support;

import java.util.Map;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午03:57:27
 * 
 */
public interface BizworkAttributes {

	public Map<String,Object> getAllAttributes(String targetService,String username,String tgt);
	
}


