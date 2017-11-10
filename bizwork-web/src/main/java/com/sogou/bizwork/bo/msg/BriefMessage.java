package com.sogou.bizwork.bo.msg;

public class BriefMessage {
    
    private Integer num;
    private Integer mesTypeId;
    private String mesTypeName;
    private Integer parentMesTypeId;
    private String parentMesTypeName;
    private String url;
    
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
    public String getMesTypeName() {
        return mesTypeName;
    }
    public void setMesTypeName(String mesTypeName) {
        this.mesTypeName = mesTypeName;
    }
    public Integer getParentMesTypeId() {
        return parentMesTypeId;
    }
    public void setParentMesTypeId(Integer parentMesTypeId) {
        this.parentMesTypeId = parentMesTypeId;
    }
    public String getParentMesTypeName() {
        return parentMesTypeName;
    }
    public void setParentMesTypeName(String parentMesTypeName) {
        this.parentMesTypeName = parentMesTypeName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
