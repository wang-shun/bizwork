package com.sogou.bizwork.bo.msg.req;

public class ComplexMsgPojo {
    private String sid;
    private SysMsgPojo data;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public SysMsgPojo getData() {
        return data;
    }

    public void setData(SysMsgPojo data) {
        this.data = data;
    }
}
