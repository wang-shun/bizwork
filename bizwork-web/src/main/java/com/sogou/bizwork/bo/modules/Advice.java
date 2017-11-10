package com.sogou.bizwork.bo.modules;

import java.util.List;

public class Advice {

    private String comment;
    private List<String> picUrl;
    private String time;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
