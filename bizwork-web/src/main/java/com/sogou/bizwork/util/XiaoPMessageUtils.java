package com.sogou.bizwork.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

public class XiaoPMessageUtils {
    public static String sendPostRequest(String url, Map<String, Object> params) {
        HttpClient client = new HttpClient();
        // 链接超时
        client.getHttpConnectionManager().getParams().setConnectionTimeout(40000);
        // 读取超时
        client.getHttpConnectionManager().getParams().setSoTimeout(40000);
        PostMethod post = new PostMethod(url);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            // System.out.println(entry.getKey() + "::::" + entry.getValue());
            post.setParameter(entry.getKey(), (String) entry.getValue());
        }
        String response = "";
        try {
            client.executeMethod(post);
            response = post.getResponseBodyAsString();
//            if (post.getStatusCode() == HttpStatus.SC_OK) {
//                response = "发送成功";
//            } else {
//                response = "发送失败";
//            }
        } catch (IOException e) {
            response = "发送失败";
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return response;
    }
    public static String sendPostRequest(String url, Map<String, Object> params, String encoding) {
    	String result = "";
    	HttpPost httpPost = new HttpPost(url);
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	for (Map.Entry<String, Object> entry : params.entrySet()) {
    		String name = entry.getKey();
    		String value = (String) entry.getValue();
    		nameValuePairs.add(new BasicNameValuePair(name, value));
    	}
    	try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
    		HttpResponse response = new DefaultHttpClient().execute(httpPost);
    		HttpEntity entity = response.getEntity();
    		result = EntityUtils.toString(entity).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        HttpClient client = new HttpClient();
//        // 链接超时
//        client.getHttpConnectionManager().getParams().setConnectionTimeout(40000);
//        // 读取超时
//        client.getHttpConnectionManager().getParams().setSoTimeout(40000);
//        PostMethod post = new PostMethod(url);
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            // System.out.println(entry.getKey() + "::::" + entry.getValue());
//            post.setParameter(entry.getKey(), (String) entry.getValue());
//        }
//        String response = "";
//        try {
//            client.executeMethod(post);
//            response = post.getResponseBodyAsString();
////            if (post.getStatusCode() == HttpStatus.SC_OK) {
////                response = "发送成功";
////            } else {
////                response = "发送失败";
////            }
//        } catch (IOException e) {
//            response = "发送失败";
//            e.printStackTrace();
//        } finally {
//            post.releaseConnection();
//        }
        return result;
    }
    
    public static String getToken(String pubid, String APIToken, String ts) {
        String str = pubid + ":" + APIToken + ":" + ts;
        String res = null;
        try {
            res = MD5(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getTimeStamp() {
        Date d = new Date();
        String s = String.valueOf(d.getTime());
        return s;
    }
    
    private static String MD5(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }
    
}
