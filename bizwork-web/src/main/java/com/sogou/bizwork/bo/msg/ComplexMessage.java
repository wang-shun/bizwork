package com.sogou.bizwork.bo.msg;

public class ComplexMessage {
    
    private Long mesId;
    private Integer mesTypeId;
    private String title;
    private String url;
    private Integer parentMesTypeId;
    private String parentMesTypeName;
    
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
    
    
}
