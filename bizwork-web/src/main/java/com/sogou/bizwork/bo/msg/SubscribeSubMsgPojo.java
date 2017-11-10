package com.sogou.bizwork.bo.msg;


/**
 * 子类型消息类
 * @author linxionghui
 *
 */
public class SubscribeSubMsgPojo {
    private Long mesId;
    private Integer mesTypeId;
    private String title;
    private String url;
    private Integer count;
    private boolean isBriefMesType;
    
    public Long getMesId() {
        return mesId;
    }
    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public boolean isBriefMesType() {
        return isBriefMesType;
    }
    public void setBriefMesType(boolean isBriefMesType) {
        this.isBriefMesType = isBriefMesType;
    }
    
}