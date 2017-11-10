package com.sogou.bizwork.bo.user;

import java.util.List;

public class UserQueryResult {
	
	private List<UserQueryInfo> list;
	
	public List<UserQueryInfo> getList() {
		return list;
	}

	public void setList(List<UserQueryInfo> list) {
		this.list = list;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	private Integer totalNumber;
	
}
