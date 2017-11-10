package com.sogou.bizwork.bo.msg;

public class MsgReceiver {

    private Long mesId;
    private Integer employeeId;


    public Long getMesId() {
        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    @Override
    public String toString() {
        return "{mesId:" + mesId
                + ", employeeId:" + employeeId + "}";
    }
}
