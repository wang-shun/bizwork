package com.sogou.bizwork.bo.modules;

//记录一个人一段时间（startdate~enddate）项目完成的情况
public class PersonalSituation {

    private Integer id;
    private Integer employee_id;
    private String projectname;
    private Integer totalline;
    private String startdate;
    private String enddate;

    public PersonalSituation() {
        super();
    }

    public PersonalSituation(Integer employee_id, String projectname, Integer totalline, String startdate,
            String enddate) {
        super();
        this.employee_id = employee_id;
        this.projectname = projectname;
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

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
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

    @Override
    public String toString() {
        return "PersonalSituation [id=" + id + ", employee_id=" + employee_id + ", projectname=" + projectname
                + ", totalline=" + totalline + ", startdate=" + startdate + ", enddate=" + enddate + "]";
    }

}
