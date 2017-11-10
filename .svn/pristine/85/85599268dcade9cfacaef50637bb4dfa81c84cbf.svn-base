package com.sogou.bizwork.cas.application.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.services.RegisteredService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.sogou.bizwork.cas.application.model.Application;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-17 下午09:05:05
 * 类说明
 */
public class ApplicationService extends Application implements
		RegisteredService, Comparable<RegisteredService> {




	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
	
	private Application application;
	
	public ApplicationService(Application application) {
		this.application = application;
	}
	
	public String getIntranetUrl(){
		return application.getIntraneturl();
	}
	public void setIntranetUrl(String intranetUrl){
		application.setIntraneturl(intranetUrl);
	}
	public Integer getAppId() {
		return application.getAppId();
	}

	public void setAppId(Long appId) {
		application.setAppId(appId.intValue());
	}

	public String getServiceId() {
		return application.getServiceId();
	}

	public void setServiceId(String serviceId) {
		application.setServiceId(serviceId);
	}

	public String getName() {
		return application.getName();
	}

	public void setName(String name) {
		application.setName(name);
	}



	public String getDescription() {
		return application.getDescription();
	}

	public void setDescription(String description) {
		application.setDescription(description);
	}

	public Date getCreateDate() {
		return application.getCreateDate();
	}

	public void setCreateDate(Date createDate) {
		application.setCreateDate(createDate);
	}

	public Date getChangeDate() {
		return application.getChangeDate();
	}

	public void setChangeDate(Date changeDate) {
		application.setChangeDate(changeDate);
	}

	public int hashCode() {
		return application.hashCode();
	}

	public boolean equals(Object obj) {
		return application.equals(obj);
	}

	public String toString() {
		return application.toString();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -6618610180732639191L;





	public List<String> getAllowedAttributes() {
		return Collections.emptyList();
	}



	
	public long getId() {
		return getAppId()==null? -1:getAppId();
	}


	public boolean isSsoEnabled() {
		return true;
	}


	public boolean matches(Service service) {

		return service != null && service.getId()!=null && service.getId().equals(getServiceId().toLowerCase());//PATH_MATCHER.match(getServiceId().toLowerCase(), getServiceUrl(service.getId().toLowerCase().trim()));
	}
    

    
    public static String getServiceUrl(final String url){
    	int loc = url.indexOf('/', 9);
    	return url.substring(0, loc);
    }


	public int compareTo(RegisteredService o) {
            return (int)(this.getId() - o.getId());

	}


	public boolean isEnabled() {
		return true;
	}


	public boolean isAnonymousAccess() {
		return false;
	}


	public boolean isIgnoreAttributes() {
		return false;
	}


	public boolean isAllowedToProxy() {
		return false;
	}


	public String getTheme() {
		return "theme";
	}


	public int getEvaluationOrder() {
		return 0;
	}
	
	public Object clone() throws CloneNotSupportedException {
 		return null;
 	}

}
