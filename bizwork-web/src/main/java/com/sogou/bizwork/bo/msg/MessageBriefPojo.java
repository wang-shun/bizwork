package com.sogou.bizwork.bo.msg;

import java.util.List;

public class MessageBriefPojo {
    private Integer mesTypeId;
    private List<MessageBrief> messageBriefs;
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
    public List<MessageBrief> getMessageBriefs() {
        return messageBriefs;
    }
    public void setMessageBriefs(List<MessageBrief> messageBriefs) {
        this.messageBriefs = messageBriefs;
    }
}
