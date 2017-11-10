package com.sogou.bizwork.cas.user.model;

public class Group {
    private Long id;

    private String groupName;

    private String chineseName;

    private Integer parentGroupId;

    private Integer parentGroup;

    private String leader;

    private String leaderEmail;

    private String groupEmail;

    private Integer groupFunction;

    private Integer groupType;

    private Integer groupeState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public Integer getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(Integer parentGroup) {
        this.parentGroup = parentGroup;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public String getLeaderEmail() {
        return leaderEmail;
    }

    public void setLeaderEmail(String leaderEmail) {
        this.leaderEmail = leaderEmail == null ? null : leaderEmail.trim();
    }

    public String getGroupEmail() {
        return groupEmail;
    }

    public void setGroupEmail(String groupEmail) {
        this.groupEmail = groupEmail == null ? null : groupEmail.trim();
    }

    public Integer getGroupFunction() {
        return groupFunction;
    }

    public void setGroupFunction(Integer groupFunction) {
        this.groupFunction = groupFunction;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getGroupeState() {
        return groupeState;
    }

    public void setGroupeState(Integer groupeState) {
        this.groupeState = groupeState;
    }
}