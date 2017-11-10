package com.sogou.bizwork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sogou.bizwork.bo.SystemItem;

/**
 * 首页DAO
 * @author zhangyu
 *
 */
@Repository
public interface SystemServiceDao {

    /**
     * 获取系统服务
     * @return
     */
    public List<SystemItem> getSystemServices(@Param("userEmail") String userEmail);

    /**
     * 获取系统服务分类
     * @return
     */
    public List<SystemItem> getSystemCategory();

    /**
     * 获取用户自定义服务
     */
    public List<SystemItem> getUserServices(@Param("userEmail") String userEmail);

    /**
     * 新增自定义系统服务
     */
    public void addUserServices(SystemItem systemItem);

    /**
     * 删除自定义系统服务
     */
    public void deleteUserServices(SystemItem systemItem);

    /**
     * 删除系统默认服务
     */
    public void deleteSystemServices(SystemItem systemItem);
}
