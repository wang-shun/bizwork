package com.sogou.bizwork.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.bizwork.bo.SystemItem;
import com.sogou.bizwork.dao.SystemServiceDao;
import com.sogou.bizwork.service.SystemService;

/**
 * 系统服务Service
 * @author zhangyu
 *
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemServiceDao systemServiceDao;

    @Override
    public List<SystemItem> getSystemServices(String userEmail) {
        List<SystemItem> systemItemTmps = systemServiceDao.getSystemServices(userEmail);
        List<SystemItem> systemItemUsers = systemServiceDao.getUserServices(userEmail);
        systemItemTmps.addAll(systemItemUsers);
        return systemItemTmps;
    }

    @Override
    public void addUserServices(SystemItem systemItem) {
        systemServiceDao.addUserServices(systemItem);
    }

    @Override
    public void deleteUserServices(SystemItem systemItem) {
        if (systemItem.getType() == 1) {
            systemServiceDao.deleteUserServices(systemItem);
        } else if (systemItem.getType() == 2) {
            systemServiceDao.deleteSystemServices(systemItem);
        }

    }

    @Override
    public List<SystemItem> getSystemCategory() {
        List<SystemItem> systemItemCategory = systemServiceDao.getSystemCategory();
        return systemItemCategory;
    }
}
