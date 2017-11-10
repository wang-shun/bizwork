package com.sogou.bizwork.user.po;

public class UserAndGroupPo {


    private long id; // employee_id
    private String name; // username  or  groupname  depends on type
    private String label; // chinesename   or  groupname  depends on type
    private int type;// 0：User 1：group

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String chinesename) {
        this.label = chinesename;
    }
}
