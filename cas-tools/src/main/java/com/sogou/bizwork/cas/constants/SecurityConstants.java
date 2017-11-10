package com.sogou.bizwork.cas.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-12 下午02:52:55
 * 
 */
public class SecurityConstants {
	/**
	 * 邮件发送线程池最小线程数
	 */
	public static Integer MAIL_POOL_COREPOOLSIZE = null;
	/**
	 * 邮件发送线程池最大线程数
	 */
	public static Integer MAIL_POOL_MAXPOOLSIZE = null;
	/**
	 * 邮件发送线程池缓冲队列大小
	 */
	public static Integer MAIL_POOL_WORKQUEUESIZE = null;
	
	/**
	 * 邮件发送线程池空闲线程超时时间
	 */
	public static Integer MAIL_POOL_KEEPALIVETIME = null;
	
	public static String AES_KEY="123";
	
	static {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = SecurityConstants.class
					.getResourceAsStream("/security-constants-config.properties");

			p.load(is);

			try {
				MAIL_POOL_COREPOOLSIZE = Integer.parseInt(p
						.getProperty("constants.mail_pool_corePoolSize"));
				MAIL_POOL_MAXPOOLSIZE = Integer.parseInt(p
						.getProperty("constants.mail_pool_maximumPoolSize"));
				MAIL_POOL_WORKQUEUESIZE = Integer.parseInt(p
						.getProperty("constants.mail_pool_workQueueSize"));
				MAIL_POOL_KEEPALIVETIME = Integer.parseInt(p
						.getProperty("constants.mail_pool_keepAliveTime"));
				AES_KEY=p.getProperty("AESkey");
				
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
