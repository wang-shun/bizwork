package com.sogou.bizwork.bo;

/**
 * 用户对象
 * @author zhangyu
 *
 */
public class SidUser {

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private User userInfo;
    private String sid;

}
