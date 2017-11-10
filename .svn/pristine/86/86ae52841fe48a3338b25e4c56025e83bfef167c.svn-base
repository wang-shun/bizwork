package com.sogou.bizwork.cas.application.dto;

import java.util.Date;



public class LogonLogDto {
	private Long accountid ;
	
	private String accountname;
	
	private String serviceurl;
	
	private String userip ;
	
	private Date logontime;
	
	private String status ;
	
	private String reason ;
	
	private Integer userType;
	
	
	
	/**
	 * 获取验证码 
	 * @author sixiaolin
	 * do not setting id too large ,<100 & int.
	 */
	public static enum TypeEnum {
		YESTERDAY("昨日", 0), TODAY("今日", 1), LAST7SEVENDAYS("过去7天", 2), THISMONTH("本月", 3), LASTMONTH("上月", 4), CUSTOM("自定义", 5);
		String text;
		int index;
		public String getText() {
			return text;
		}

		public int getType() {
			return index;
		}

		private TypeEnum(String text, int index) {
			this.text = text;
			this.index = index;
		}
		public static TypeEnum parse(int i) {
			for (TypeEnum type : values()) {
				if (type.index == i) {
					return type;
				}
			}
			return null;
		}
	}

	/**
	 * @return the accountid
	 */
	public Long getAccountid() {
		return accountid;
	}

	/**
	 * @param accountid the accountid to set
	 */
	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	/**
	 * @return the accountname
	 */
	public String getAccountname() {
		return accountname;
	}

	/**
	 * @param accountname the accountname to set
	 */
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	/**
	 * @return the serviceurl
	 */
	public String getServiceurl() {
		return serviceurl;
	}

	/**
	 * @param serviceurl the serviceurl to set
	 */
	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}

	/**
	 * @return the userip
	 */
	public String getUserip() {
		return userip;
	}

	/**
	 * @param userip the userip to set
	 */
	public void setUserip(String userip) {
		this.userip = userip;
	}

	/**
	 * @return the logontime
	 */
	public Date getLogontime() {
		return logontime;
	}

	/**
	 * @param logontime the logontime to set
	 */
	public void setLogontime(Date logontime) {
		this.logontime = logontime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	public LogonLogDto() {
		super();
	}

	public LogonLogDto(Long accountid, String accountname, String serviceurl,
			String userip, Date logontime, String status, String reason) {
		super();
		this.accountid = accountid;
		this.accountname = accountname;
		this.serviceurl = serviceurl;
		this.userip = userip;
		this.logontime = logontime;
		this.status = status;
		this.reason = reason;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogonLogDto [accountid=" + accountid + ", accountname="
				+ accountname + ", serviceurl=" + serviceurl + ", userip="
				+ userip + ", logontime=" + logontime + ", status=" + status
				+ ", reason=" + reason + "]";
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	
}
