package com.sogou.bizwork.bo.msg;

public class SubscribeMessageType {
    private String mesTypeName;
    private Integer mesTypeId;
    private boolean ifSubscribe;
    
    public String getMesTypeName() {
        return mesTypeName;
    }
    public void setMesTypeName(String mesTypeName) {
        this.mesTypeName = mesTypeName;
    }
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
    public boolean getIfSubscribe() {
        return ifSubscribe;
    }
    public void setIfSubscribe(Integer ifSubscribe) {  //给前端返回true/false
        if (ifSubscribe == 1) {  //1不订阅
            this.ifSubscribe = false;
        } else {  //0订阅
            this.ifSubscribe = true;
        }
    }
}
