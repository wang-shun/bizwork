package com.sogou.bizwork.cas.web.support;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.web.support.AbstractSingleSignOutEnabledArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogou.bizwork.cas.application.service.ApplicationService;



/**
 * 
 * @author liujianBJ7368
 * @since 2012-05-18
 */
public class BizworkArgumentExtractor extends AbstractSingleSignOutEnabledArgumentExtractor {

	Logger log = LoggerFactory.getLogger(BizworkArgumentExtractor.class);
	
	private ConcurrentHashMap<String, String> extranetToIntranet = new ConcurrentHashMap<String, String>();
	
	private ServicesManager servicesManager;
	
	public BizworkArgumentExtractor(ServicesManager servicesManager){
		this.servicesManager = servicesManager;
		load2Domain();
		
	}
	

	public final WebApplicationService extractServiceInternal(final HttpServletRequest request) {
        return BizworkWebApplicationServiceImpl.createServiceFrom(request, extranetToIntranet,getHttpClientIfSingleSignOutEnabled());
    }
 
	private void load2Domain() {
		final ConcurrentHashMap<String, String> extranet2Intranet = new ConcurrentHashMap<String, String>();
		Collection<RegisteredService> services = servicesManager.getAllServices();
		if(null != services && !services.isEmpty()){
			StringBuffer sb = new StringBuffer(128);
			sb.append("load domainMap:");
			for(RegisteredService service :services){
				ApplicationService applicationService = (ApplicationService)service;
				
				extranet2Intranet.put(applicationService.getServiceId(), 
						applicationService.getIntranetUrl()!=null?applicationService.getIntranetUrl():applicationService.getServiceId() );
				sb.append(applicationService.getServiceId()+"->"+ applicationService.getIntranetUrl()+"\t\n");
			}
			log.info(sb.toString());
		}
		
		extranetToIntranet = extranet2Intranet;
	}
    
}