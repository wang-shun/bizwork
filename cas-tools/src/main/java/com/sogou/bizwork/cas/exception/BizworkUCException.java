package com.sogou.bizwork.cas.exception;
/**
 * @author sixiaolin
 * @version 创建时间：2016-7-13 上午11:01:50
 * 
 */
public class BizworkUCException extends Exception{

	private static final long serialVersionUID = -7309417544765541852L;


	private ErrorInfo errorInfo;
	private Integer errorCode;
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public BizworkUCException(Integer code) {
		super();
		this.errorCode = code;
		errorInfo = new ErrorInfo();
		if(null != code) {
			this.errorInfo.setErrorCode(code);
		}
	}
	
	public BizworkUCException(Integer code, String errorMsg) {
		super();
		this.errorCode = code;
		this.errorInfo = new ErrorInfo();
		if(null != code) {
				this.errorInfo.setErrorCode(code);				
			} if(null != errorMsg) {
				this.errorInfo.setErrorMsg(errorMsg);	
			}
	}
	
	public BizworkUCException(Integer code, String errorMsg,String detailMsg) {
		super();
		this.errorCode = code;
		this.errorInfo = new ErrorInfo();
		if(null != code) {
				this.errorInfo.setErrorCode(code);				
			} if(null != errorMsg) {
				this.errorInfo.setErrorMsg(errorMsg);	
			}if(null != errorMsg) {
				this.errorInfo.setDetailMsg(detailMsg);	
			}
	}
	
	public BizworkUCException(ErrorInfo errorInfo) {
		super();
		this.errorInfo = errorInfo;
	}

	public BizworkUCException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getMessage() {
		return ErrorCode.getMsg(errorCode);
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
