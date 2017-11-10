package com.sogou.bizwork.constant;

public enum UserStatusEnum {

    ONLINE(0, "在线"), OFFLINE(1, "不在线");

    private int id;
    private String text;

    private UserStatusEnum(int id, String text) {
        this.text = text;
        this.id = id;
    }

    public static String getText(int id) {
        for (UserStatusEnum e : UserStatusEnum.values()) {
            if (e.getId() == id) {
                return e.text;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
