package com.sogou.bizwork.interceptor;


public class ExecuteTimeHolder {

    private static ThreadLocal<Long> startTimeHolder = new ThreadLocal<Long>();

    public static long getStartTime() {
        return startTimeHolder.get();
    }

    public static void setStartTime(Long startTime) {
        startTimeHolder.set(startTime);
    }

}
