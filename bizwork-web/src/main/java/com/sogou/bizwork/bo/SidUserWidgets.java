package com.sogou.bizwork.bo;

import java.util.List;

/**
 * 首頁
 * @author zhangyu
 *
 */
public class SidUserWidgets {

    private String sid;
    private List<UserWidget> userWidgets;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<UserWidget> getUserWidgets() {
        return userWidgets;
    }

    public void setUserWidgets(List<UserWidget> userWidgets) {
        this.userWidgets = userWidgets;
    }

}
