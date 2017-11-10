package com.sogou.bizwork.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.HomepageWidget;
import com.sogou.bizwork.bo.UserWidget;

/**
 * 首页DAO
 * @author zhangyu
 *
 */
@Repository
public interface HomepageDao {

    /**
     * 
     * @param username
     * @return
     */
    public List<HomepageWidget> getWidgets(String userEmail);

    public List<HomepageWidget> getDefaultWidgets();

    /**
     * 
     * @param username
     * @return
     */
    public List<HomepageWidget> getNoSubscribeWidgets(String userEmail);

    public void deleteUserWidgets(String userEmail);

    public void addUserWidgets(UserWidget userWidget);
}
