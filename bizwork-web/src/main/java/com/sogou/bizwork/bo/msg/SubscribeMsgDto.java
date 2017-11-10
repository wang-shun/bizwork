package com.sogou.bizwork.bo.msg;

import java.util.List;

public class SubscribeMsgDto {
    private int totalCount;
    private List<SubscribeMsgPojo> mesList;
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public List<SubscribeMsgPojo> getMesList() {
        return mesList;
    }
    public void setMesList(List<SubscribeMsgPojo> mesList) {
        this.mesList = mesList;
    }
    
    
}
