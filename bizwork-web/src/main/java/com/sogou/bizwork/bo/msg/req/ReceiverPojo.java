package com.sogou.bizwork.bo.msg.req;

public class ReceiverPojo {
    private boolean subscribe;
    private Integer mesTypeId;
    
    public boolean isSubscribe() {
        return subscribe;
    }
    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
}
