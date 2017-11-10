package com.sogou.bizwork.bo.msg;

import java.util.List;

import com.sogou.bizwork.api.message.BriefTypeMessage;

public class BriefMessagePojo {
    private Integer mesTypeId;
    private List<BriefTypeMessage> briefTypeMessages;
    
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
    public List<BriefTypeMessage> getBriefTypeMessages() {
        return briefTypeMessages;
    }
    public void setBriefTypeMessages(List<BriefTypeMessage> briefTypeMessages) {
        this.briefTypeMessages = briefTypeMessages;
    }
}
