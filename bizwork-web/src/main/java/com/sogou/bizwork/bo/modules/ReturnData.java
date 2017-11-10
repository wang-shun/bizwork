package com.sogou.bizwork.bo.modules;

import java.util.List;

//返回给首页的json对象
public class ReturnData {

    List<String> colname;
    List<ColVal> colval;

    public List<String> getColname() {
        return colname;
    }

    public void setColname(List<String> colname) {
        this.colname = colname;
    }

    public List<ColVal> getColval() {
        return colval;
    }

    public void setColval(List<ColVal> colval) {
        this.colval = colval;
    }

}
