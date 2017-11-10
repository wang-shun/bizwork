package com.sogou.bizwork.cas.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午02:49:08
 * 
 */
public abstract class ThreadLocalHolder {
	
	private static final Logger log = Logger.getLogger(ThreadLocalHolder.class);
	
	private static final ThreadLocal<Map<Object, Object>> threadLocalResources = new ThreadLocal<Map<Object, Object>>();

	public static Map<Object, Object> getThreadMap() {
		Map<Object, Object> threadMap = threadLocalResources.get();
		if (threadMap == null) {
			threadMap = new HashMap<Object, Object>();
			threadLocalResources.set(threadMap);
		}

		return threadMap;
	}
	
	public static Object getProperty(Object key) {
		if (key == null) 
			throw new IllegalArgumentException("Parameter must not be null");

		Map<Object, Object> queryMap = getThreadMap();
		return queryMap.get(key);
	}
	
	public static void bindProperty(Object key, Object target) {
		if (key == null) 
			throw new IllegalArgumentException("Parameter must not be null");

		Map<Object, Object> propertiesMap = getThreadMap();
		propertiesMap.put(key, target);

		if (log.isDebugEnabled()) {
			log.debug("Bound Object (" + key +", " + target + ") to thread [" + Thread.currentThread().getName() + "]");
		}
	}
	
	public static void unbindProperty(Object key) {
		if (key == null) 
			throw new IllegalArgumentException("Parameter must not be null");

		Map<Object, Object> propertiesMap = getThreadMap();
		if (!propertiesMap.containsKey(key)) {
			if (log.isDebugEnabled()) {
				log.debug("Removed value [" + key + "] from thread [" + Thread.currentThread().getName() + "]");
			}
		}
		propertiesMap.remove(key);

		if (log.isDebugEnabled()) {
			log.debug("Removed value [" + key + "] from thread [" + Thread.currentThread().getName() + "]");
		}
	}
}


