package com.sogou.bizwork.bo.modules;

//记录一个组一段时间（startdate~enddate）项目完成的情况
public class GroupSituation {

    private Integer id;
    private Integer groupid;
    private String projectname;
    private Integer totalline;
    private String startdate;
    private String enddate;

    public GroupSituation() {
        super();
    }

    public GroupSituation(Integer groupid, String projectname, Integer totalline, String startdate, String enddate) {
        super();
        this.groupid = groupid;
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

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
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
        return "GroupSituation [id=" + id + ", groupid=" + groupid + ", projectname=" + projectname + ", totalline="
                + totalline + ", startdate=" + startdate + ", enddate=" + enddate + "]";
    }

}
