package com.sogou.bizwork.bo.user;

public class GroupInfo {

	private Integer groupId;
	
	private String groupName;
	
	private String groupAuth;
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupAuth() {
		return groupAuth;
	}

	public void setGroupAuth(String groupAuth) {
		this.groupAuth = groupAuth;
	}

	public Integer getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Integer parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	private Integer parentGroupId;
	
}
