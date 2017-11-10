package com.sogou.bizwork.bo.msg;

public class MessageTypeRelation {
    
    private String mesTypeName;
    private Integer mesTypeId;
    
    private String subMesTypeName;
    private Integer subMesTypeId;
    
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
    public String getSubMesTypeName() {
        return subMesTypeName;
    }
    public void setSubMesTypeName(String subMesTypeName) {
        this.subMesTypeName = subMesTypeName;
    }
    public Integer getSubMesTypeId() {
        return subMesTypeId;
    }
    public void setSubMesTypeId(Integer subMesTypeId) {
        this.subMesTypeId = subMesTypeId;
    }
    
}
