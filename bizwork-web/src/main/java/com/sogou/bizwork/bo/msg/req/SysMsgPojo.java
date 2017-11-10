package com.sogou.bizwork.bo.msg.req;


public class SysMsgPojo {
    private Integer mesTypeId;
    private boolean onlyMySend;
    private String startDate;
    private String endDate;
    private int pageNo;
    private int pageSize;
    public Integer getMesTypeId() {
        return mesTypeId;
    }
    public void setMesTypeId(Integer mesTypeId) {
        this.mesTypeId = mesTypeId;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public boolean isOnlyMySend() {
        return onlyMySend;
    }
    public void setOnlyMySend(boolean onlyMySend) {
        this.onlyMySend = onlyMySend;
    }
    @Override
    public String toString() {
        return "SysMsgPojo [mesTypeId=" + mesTypeId + ", onlyMySend="
                + onlyMySend + ", startDate=" + startDate + ", endDate="
                + endDate + ", pageNo=" + pageNo + ", pageSize=" + pageSize
                + "]";
    }
}
