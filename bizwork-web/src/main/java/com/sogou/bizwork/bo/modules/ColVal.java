package com.sogou.bizwork.bo.modules;

//返回的json对象数据实体
public class ColVal {

    String key;
    double value;

    public ColVal(String key, double value) {
        super();
        this.key = key;
        this.value = value;
    }

    public ColVal() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
