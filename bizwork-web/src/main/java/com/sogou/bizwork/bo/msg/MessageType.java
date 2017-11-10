package com.sogou.bizwork.bo.msg;

public class MessageType {
    private Integer mesTypeId;

    private String mesTypeName;

    private Integer employeeId;

    private String url;

    private Integer type;

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
        this.mesTypeName = mesTypeName == null ? null : mesTypeName.trim();
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}