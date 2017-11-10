package com.sogou.bizwork.cas.utils;
/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午03:57:27
 * 
 */
public class StringUtils {
	
    public static boolean isEmpty(final String string) {
        return string == null ||string==""|| string.length() == 0;
    }
    
    public static boolean isBlank(final String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return true;
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return false;
			}
		}
		return true;
    }
    
    public static boolean isNotBlank(final String string) {
    	return !isBlank(string);
    }

}
