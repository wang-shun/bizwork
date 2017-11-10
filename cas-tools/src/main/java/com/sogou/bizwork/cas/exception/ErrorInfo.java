package com.sogou.bizwork.cas.exception;


public class ErrorInfo {

	 
	private Integer errorCode ;
	private String errorMsg;
	private String detailMsg;
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getDetailMsg() {
		return detailMsg;
	}
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((detailMsg == null) ? 0 : detailMsg.hashCode());
		result = prime * result
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result
				+ ((errorMsg == null) ? 0 : errorMsg.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorInfo other = (ErrorInfo) obj;
		if (detailMsg == null) {
			if (other.detailMsg != null)
				return false;
		} else if (!detailMsg.equals(other.detailMsg))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorMsg == null) {
			if (other.errorMsg != null)
				return false;
		} else if (!errorMsg.equals(other.errorMsg))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ErrorInfo [errorCode=" + errorCode + ", errorMsg=" + errorMsg
				+ ", detailMsg=" + detailMsg + "]";
	}

	 

}
