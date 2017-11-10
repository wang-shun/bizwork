package com.sogou.bizwork.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.bizwork.bo.HomepageWidget;
import com.sogou.bizwork.bo.UserWidget;
import com.sogou.bizwork.dao.HomepageDao;
import com.sogou.bizwork.service.HomepageService;

/**
 * 首页管理Service的实现
 * @author dongzeguang
 *
 */
@Service
public class HomepageServiceImpl implements HomepageService {

    @Autowired
    private HomepageDao homepageDao;

    @Override
    public List<HomepageWidget> createAndGetWidgets(String userEmail) {
        List<HomepageWidget> homepageWidgets = homepageDao.getWidgets(userEmail);
        if (homepageWidgets.size() == 0) {
            List<HomepageWidget> defaultWidgets = getDefaultWidgets();
            for (int i = 0; i < defaultWidgets.size(); i++) {
                homepageDao.addUserWidgets(new UserWidget(userEmail, defaultWidgets.get(i).getId(), i + 1));
            }
            homepageWidgets = homepageDao.getWidgets(userEmail);
        }
        return homepageWidgets;
    }

    @Override
    public List<HomepageWidget> getDefaultWidgets() {
        return homepageDao.getDefaultWidgets();
    }

    @Override
    public List<HomepageWidget> getNoSubscribeWidgets(String userEmail) {
        return homepageDao.getNoSubscribeWidgets(userEmail);
    }

    @Override
    public void releaseWidgets(List<UserWidget> userWidgets, String userEmail) {
        homepageDao.deleteUserWidgets(userEmail);
        for (UserWidget userWidget : userWidgets) {
            userWidget.setUserEmail(userEmail);
            homepageDao.addUserWidgets(userWidget);
        }
    }
}
