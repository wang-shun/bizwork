package com.sogou.bizwork.bo.modules;

import com.sogou.bizwork.bo.User;

 
public class Project {

    private Integer projectid;
    private User user;
    private String projectname;
    private String svnurl;
    private Integer totalline;

    public Project() {
        super();
    }

    public Project(Integer projectid, User user, String projectname, String svnurl, Integer totalline) {
        super();
        this.projectid = projectid;
        this.user = user;
        this.projectname = projectname;
        this.svnurl = svnurl;
        this.totalline = totalline;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getSvnurl() {
        return svnurl;
    }

    public void setSvnurl(String svnurl) {
        this.svnurl = svnurl;
    }

    public Integer getTotalline() {
        return totalline;
    }

    public void setTotalline(Integer totalline) {
        this.totalline = totalline;
    }

}
