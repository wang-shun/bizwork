package com.sogou.bizwork.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvUtil {

    public static final long version = System.currentTimeMillis();

    public static boolean loginDebug = false;
    public static String downloadUrlIOS;
    public static String downloadUrlAndroid;

    static {
        Properties prop = new Properties();
        InputStream in = EnvUtil.class.getResourceAsStream("/env.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String loginDebugStr = prop.getProperty("loginDebug");
        if ("true".equalsIgnoreCase(loginDebugStr)) {
            loginDebug = true;
        }
        
        downloadUrlIOS = prop.getProperty("downloadUrl.ios");
        downloadUrlAndroid = prop.getProperty("downloadUrl.android");
    }

}
