package com.sogou.bizwork.service;

import java.util.List;

import com.sogou.bizwork.bo.HomepageWidget;
import com.sogou.bizwork.bo.UserWidget;

/**
 * 首页管理Service
 * @author dongzeguang
 *
 */
public interface HomepageService {

    public List<HomepageWidget> createAndGetWidgets(String userEmail);

    public List<HomepageWidget> getDefaultWidgets();

    public List<HomepageWidget> getNoSubscribeWidgets(String userEmail);

    public void releaseWidgets(List<UserWidget> userWidgets, String userEmail);
}
