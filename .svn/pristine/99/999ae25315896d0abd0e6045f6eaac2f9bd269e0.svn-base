package com.sogou.bizwork.cas.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-15 下午01:51:59
 * 
 */
public class JasonUtils {

    /** 
    * 从json HASH表达式中获取一个map，该map支持嵌套功能 
    * 形如：{"id" : "johncon", "name" : "小强"} 
    * 注意commons-collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap 
    * @param object 
    * @return 
    */
    public static Map getMapFromJson(String jsonString) {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
            String key = (String) iter.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    public static String getUserNameFromJson(String response) {
        JSONObject jsonObject = JSONObject.fromObject(response).getJSONObject("data");
        String useName = jsonObject.getString("uid");
        return useName;

    }

}
