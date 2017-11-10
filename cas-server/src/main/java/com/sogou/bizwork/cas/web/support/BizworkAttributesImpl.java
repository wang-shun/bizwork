package com.sogou.bizwork.cas.web.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.services.RegisteredService;

import com.sogou.bizwork.cas.application.dao.AbstractServiceRegistryDao;
import com.sogou.bizwork.cas.application.model.AppAttribute;
import com.sogou.bizwork.cas.application.model.Application;
import com.sogou.bizwork.cas.application.model.Attribute;
import com.sogou.bizwork.cas.application.service.AppAttributeService;
import com.sogou.bizwork.cas.application.service.AttributeService;
import com.sogou.bizwork.cas.application.service.BizworkApplicationService;
import com.sogou.bizwork.cas.application.service.BizworkAuthorizationUserService;
import com.sogou.bizwork.cas.constants.SSOConstantsCode;
import com.sogou.bizwork.cas.constants.UUCConstants;
import com.sogou.bizwork.cas.user.service.UserManageService;
import com.sogou.bizwork.cas.utils.AESForNodejs;
import com.sogou.bizwork.cas.utils.BizworkCredentials;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午03:57:27
 * 
 */
public class BizworkAttributesImpl implements BizworkAttributes, UUCConstants, SSOConstantsCode {

    private static final Logger logger = Logger.getLogger(BizworkAttributesImpl.class);

    @Resource(name = "serviceRegistry")
    private AbstractServiceRegistryDao serviceRegistry;

    @Resource(name = "bizworkApplicationService")
    private BizworkApplicationService bizworkApplicationService;

    @Resource(name = "attributeService")
    private AttributeService attributeService;

    @Resource(name = "appAttributeService")
    private AppAttributeService appAttributeService;

    @Resource(name = "userManageService")
    private UserManageService userManageService;

    @Resource(name = "bizworkAuthorizationUserService")
    private BizworkAuthorizationUserService bizworkAuthorizationUserService;

    @Override
    public Map<String, Object> getAllAttributes(String targetService, String username, String tgt) {

        Application application = bizworkApplicationService.seleApplicationByAppId(targetService);

        RegisteredService registeredservice = serviceRegistry.findServiceByServiceId(targetService);

        List<AppAttribute> appAtts = appAttributeService.getAllAttributeByAppId(application != null ? application
                .getAppId() : null);

        if (null != userManageService) {
            Map<String, Object> info = userManageService.getUserInfo(username.trim());
            if (null != info && !info.isEmpty()) {
                Map<String, Object> backAttri = new HashMap<String, Object>();
                for (AppAttribute appAtt : appAtts) {
                    Attribute att = attributeService.getAttributeById(appAtt.getAttributeId());
                    String attribute = att != null ? att.getAttributeValue() : "";
                    if (att != null) {
                        if (attribute.equals(SERVICE_ID))
                            backAttri.put(SERVICE_ID, application.getAppId());
                        if (attribute.equals(SERVICE_URI))
                            backAttri.put(SERVICE_URI, targetService);
                        if (attribute.equals(SERVICE_NAME))
                            backAttri.put(SERVICE_NAME, registeredservice.getName());

                        if ((attribute.equals(USER_EMAIL)) && (info.containsKey(USER_EMAIL)))
                            backAttri.put(USER_EMAIL, info.get(USER_EMAIL));
                        if (attribute.equals(USER_NAME) && info.containsKey(USER_NAME))
                            backAttri.put(USER_NAME, info.get(USER_NAME));
                        if (attribute.equals(USER_ID) && info.containsKey(USER_ID))
                            backAttri.put(USER_ID, info.get(USER_ID));

                        if ((attribute.equals(USER_GROUP)) && (info.containsKey(USER_GROUP)))
                            backAttri.put(USER_GROUP, info.get(USER_GROUP));
                        if ((attribute.equals(USER_LEVEL)) && (info.containsKey(USER_LEVEL)))
                            backAttri.put(USER_LEVEL, info.get(USER_LEVEL));

                        if ((attribute.equals(USER_TITLE)) && (info.containsKey(USER_TITLE)))
                            backAttri.put(USER_TITLE, info.get(USER_TITLE));
                        if ((attribute.equals(USER_PHONE)) && (info.containsKey(USER_PHONE)))
                            backAttri.put(USER_PHONE, info.get(USER_PHONE));
                        if ((attribute.equals(USER_PHOTO)) && (info.containsKey(USER_PHOTO)))
                            backAttri.put(USER_PHOTO, info.get(USER_PHOTO));
                        if ((attribute.equals(USER_EMPLOYEE_ID)) && (info.containsKey(USER_EMPLOYEE_ID)))
                            backAttri.put(USER_EMPLOYEE_ID, info.get(USER_EMPLOYEE_ID));
                    }
                }

                Attribute password = attributeService.getAttributeByValue(USER_PASSWORD);
                // 增加小P认证，修改了下面的代码，原来的代码在try里，发生catch的情况就说明这是小P来认证。
                for (AppAttribute appAttribute : appAtts) {
                    if (appAttribute.getAttributeId().equals(password.getAttributeId())) {
                        try {
                            BizworkCredentials credentials = bizworkAuthorizationUserService.queryCredentialsByTgt(tgt);
                            String pwd = credentials.getPassword();
                            backAttri.put(USER_PASSWORD, pwd);
                        } catch (Exception e) {
                            // 发生catch的情况是当小P来认证的时候，小P来认证是走该逻辑，小P没有password所以模拟一个password
                            String pwd = "ThisIsToken";
                            try {
                                // 后面的为种子，需要和其他地方对应，没有对应就无法正确解密其他应用端就无法为user对象设置密码将报空指针异常
                                pwd = AESForNodejs.encrypt(pwd, "^&YGB*8^");
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            backAttri.put(USER_PASSWORD, pwd);
                        }
                    }
                }

                StringBuilder sb = new StringBuilder(2048);
                sb.append("getting attributes for ").append("; target service:").append(targetService)
                        .append("; username:").append(username).append("; login mode:").append(tgt).append("\n");
                sb.append("\t attributes are:").append(info.toString());
                logger.info(sb.toString());

                return backAttri;
            }
        }
        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder(2048);
            sb.append("No attributes for ").append("; target service:").append(targetService).append("; username:")
                    .append(username).append("; login mode:").append(tgt);
            logger.debug(sb.toString());
        }
        return Collections.emptyMap();
    }

    public void setServiceRegistry(AbstractServiceRegistryDao serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setAttributeService(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    public void setAppAttributeService(AppAttributeService appAttributeService) {
        this.appAttributeService = appAttributeService;
    }

    public BizworkApplicationService getBizworkApplicationService() {
        return bizworkApplicationService;
    }

    public void setBizworkApplicationService(BizworkApplicationService bizworkApplicationService) {
        this.bizworkApplicationService = bizworkApplicationService;
    }

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    public AbstractServiceRegistryDao getServiceRegistry() {
        return serviceRegistry;
    }

    public AttributeService getAttributeService() {
        return attributeService;
    }

    public AppAttributeService getAppAttributeService() {
        return appAttributeService;
    }

    public BizworkAuthorizationUserService getBizworkAuthorizationUserService() {
        return bizworkAuthorizationUserService;
    }

    public void setBizworkAuthorizationUserService(BizworkAuthorizationUserService bizworkAuthorizationUserService) {
        this.bizworkAuthorizationUserService = bizworkAuthorizationUserService;
    }
}
