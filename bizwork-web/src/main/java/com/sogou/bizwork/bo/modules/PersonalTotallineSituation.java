package com.sogou.bizwork.bo.modules;

//记录一个人一段时间（startdate~enddate）项目完成的totalline和username对应的情况
public class PersonalTotallineSituation {

    private Integer id;
    private Integer groupid;
    private Integer employee_id;
    private String chinesename;
    private Integer totalline;
    private String startdate;
    private String enddate;

    public PersonalTotallineSituation() {
        super();
    }

    public PersonalTotallineSituation(Integer groupid, Integer employee_id, String chinesename, Integer totalline,
            String startdate, String enddate) {
        super();
        this.groupid = groupid;
        this.employee_id = employee_id;
        this.chinesename = chinesename;
        this.totalline = totalline;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getTotalline() {
        return totalline;
    }

    public void setTotalline(Integer totalline) {
        this.totalline = totalline;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

}
