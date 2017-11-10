package com.sogou.bizwork.bo.user;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class UserQuery {

	private String sid;
	
	private String userName;
	
	private String birthStartDate;//生日开始时间
	private String birthEndDate;//生日结束时间
	private String leaderName;//直属leader
	private Integer groupId;//所属组
	@JsonProperty(value = "HCDescription")
	private String hcDescription;//HC描述
	private Integer orderBy;// 0 默认 1 组升序 2 组降序  3 HC升序 4 HC降序
	private Integer pageSize;
	private Integer pageNo;
	private List<Integer> groupIdList;
	private List<Integer> leaderGroupIdList;
    
	//下载用
	private String reportName;
	
	public String getBirthStartDate() {
		return birthStartDate;
	}

	public void setBirthStartDate(String birthStartDate) {
		this.birthStartDate = birthStartDate;
	}

	public String getBirthEndDate() {
		return birthEndDate;
	}

	public void setBirthEndDate(String birthEndDate) {
		this.birthEndDate = birthEndDate;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getHcDescription() {
		return hcDescription;
	}

	public void setHCDescription(String hCDescription) {
		hcDescription = hCDescription;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public List<Integer> getGroupIdList() {
		return groupIdList;
	}

	public void setGroupIdList(List<Integer> groupIdList) {
		this.groupIdList = groupIdList;
	}

	public List<Integer> getLeaderGroupIdList() {
		return leaderGroupIdList;
	}

	public void setLeaderGroupIdList(List<Integer> leaderGroupIdList) {
		this.leaderGroupIdList = leaderGroupIdList;
	}
}
