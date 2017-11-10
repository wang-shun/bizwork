package com.sogou.bizwork.bo.msg;

import java.sql.Date;


public class SysMessagePojo {
    private Long mesId;
    private String mesTypeName;
    private Integer platform;
    private String title;
    private String url;
    private Date startDate;
    private Date endDate;
    private String createName;
    private boolean canRecall;
    private boolean canEdit;
    
    
    public boolean isCanRecall() {
        return canRecall;
    }
    public void setCanRecall(boolean canRecall) {
        this.canRecall = canRecall;
    }
    public Long getMesId() {
        return mesId;
    }
    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }
    public String getMesTypeName() {
        return mesTypeName;
    }
    public void setMesTypeName(String mesTypeName) {
        this.mesTypeName = mesTypeName;
    }
    public Integer getPlatform() {
        return platform;
    }
    public void setPlatform(Integer platform) {
        this.platform = platform;
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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getCreateName() {
        return createName;
    }
    public void setCreateName(String createName) {
        this.createName = createName;
    }
    public boolean isCanEdit() {
        return canEdit;
    }
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
}
