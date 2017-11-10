package com.sogou.bizwork.bo;

import java.util.List;

/**
 * 首頁
 * @author zhangyu
 *
 */
public class AllWidget {

    private List<HomepageWidget> subscribe;

    private List<HomepageWidget> unsubscribe;

    public List<HomepageWidget> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<HomepageWidget> subscribe) {
        this.subscribe = subscribe;
    }

    public List<HomepageWidget> getUnsubscribe() {
        return unsubscribe;
    }

    public void setUnsubscribe(List<HomepageWidget> unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

}
