package com.sogou.bizwork.cas.user.model;

import java.util.ArrayList;
import java.util.List;

public class GroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GroupExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("groupname is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("groupname is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("groupname =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("groupname <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("groupname >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("groupname >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("groupname <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("groupname <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("groupname like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("groupname not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("groupname in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("groupname not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("groupname between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("groupname not between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andChineseNameIsNull() {
            addCriterion("chinesename is null");
            return (Criteria) this;
        }

        public Criteria andChineseNameIsNotNull() {
            addCriterion("chinesename is not null");
            return (Criteria) this;
        }

        public Criteria andChineseNameEqualTo(String value) {
            addCriterion("chinesename =", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameNotEqualTo(String value) {
            addCriterion("chinesename <>", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameGreaterThan(String value) {
            addCriterion("chinesename >", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameGreaterThanOrEqualTo(String value) {
            addCriterion("chinesename >=", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameLessThan(String value) {
            addCriterion("chinesename <", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameLessThanOrEqualTo(String value) {
            addCriterion("chinesename <=", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameLike(String value) {
            addCriterion("chinesename like", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameNotLike(String value) {
            addCriterion("chinesename not like", value, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameIn(List<String> values) {
            addCriterion("chinesename in", values, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameNotIn(List<String> values) {
            addCriterion("chinesename not in", values, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameBetween(String value1, String value2) {
            addCriterion("chinesename between", value1, value2, "chineseName");
            return (Criteria) this;
        }

        public Criteria andChineseNameNotBetween(String value1, String value2) {
            addCriterion("chinesename not between", value1, value2, "chineseName");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdIsNull() {
            addCriterion("parentgroupid is null");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdIsNotNull() {
            addCriterion("parentgroupid is not null");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdEqualTo(Integer value) {
            addCriterion("parentgroupid =", value, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdNotEqualTo(Integer value) {
            addCriterion("parentgroupid <>", value, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdGreaterThan(Integer value) {
            addCriterion("parentgroupid >", value, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parentgroupid >=", value, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdLessThan(Integer value) {
            addCriterion("parentgroupid <", value, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("parentgroupid <=", value, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdIn(List<Integer> values) {
            addCriterion("parentgroupid in", values, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdNotIn(List<Integer> values) {
            addCriterion("parentgroupid not in", values, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("parentgroupid between", value1, value2, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parentgroupid not between", value1, value2, "parentGroupId");
            return (Criteria) this;
        }

        public Criteria andParentGroupIsNull() {
            addCriterion("parentgroup is null");
            return (Criteria) this;
        }

        public Criteria andParentGroupIsNotNull() {
            addCriterion("parentgroup is not null");
            return (Criteria) this;
        }

        public Criteria andParentGroupEqualTo(Integer value) {
            addCriterion("parentgroup =", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupNotEqualTo(Integer value) {
            addCriterion("parentgroup <>", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupGreaterThan(Integer value) {
            addCriterion("parentgroup >", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("parentgroup >=", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupLessThan(Integer value) {
            addCriterion("parentgroup <", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupLessThanOrEqualTo(Integer value) {
            addCriterion("parentgroup <=", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupLike(Integer value) {
            addCriterion("parentgroup like", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupNotLike(Integer value) {
            addCriterion("parentgroup not like", value, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupIn(List<Integer> values) {
            addCriterion("parentgroup in", values, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupNotIn(List<Integer> values) {
            addCriterion("parentgroup not in", values, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupBetween(Integer value1, Integer value2) {
            addCriterion("parentgroup between", value1, value2, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andParentGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("parentgroup not between", value1, value2, "parentGroup");
            return (Criteria) this;
        }

        public Criteria andLeaderIsNull() {
            addCriterion("leader is null");
            return (Criteria) this;
        }

        public Criteria andLeaderIsNotNull() {
            addCriterion("leader is not null");
            return (Criteria) this;
        }

        public Criteria andLeaderEqualTo(String value) {
            addCriterion("leader =", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotEqualTo(String value) {
            addCriterion("leader <>", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderGreaterThan(String value) {
            addCriterion("leader >", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderGreaterThanOrEqualTo(String value) {
            addCriterion("leader >=", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderLessThan(String value) {
            addCriterion("leader <", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderLessThanOrEqualTo(String value) {
            addCriterion("leader <=", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderLike(String value) {
            addCriterion("leader like", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotLike(String value) {
            addCriterion("leader not like", value, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderIn(List<String> values) {
            addCriterion("leader in", values, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotIn(List<String> values) {
            addCriterion("leader not in", values, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderBetween(String value1, String value2) {
            addCriterion("leader between", value1, value2, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderNotBetween(String value1, String value2) {
            addCriterion("leader not between", value1, value2, "leader");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailIsNull() {
            addCriterion("leaderemail is null");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailIsNotNull() {
            addCriterion("leaderemail is not null");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailEqualTo(String value) {
            addCriterion("leaderemail =", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailNotEqualTo(String value) {
            addCriterion("leaderemail <>", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailGreaterThan(String value) {
            addCriterion("leaderemail >", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailGreaterThanOrEqualTo(String value) {
            addCriterion("leaderemail >=", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailLessThan(String value) {
            addCriterion("leaderemail <", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailLessThanOrEqualTo(String value) {
            addCriterion("leaderemail <=", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailLike(String value) {
            addCriterion("leaderemail like", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailNotLike(String value) {
            addCriterion("leaderemail not like", value, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailIn(List<String> values) {
            addCriterion("leaderemail in", values, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailNotIn(List<String> values) {
            addCriterion("leaderemail not in", values, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailBetween(String value1, String value2) {
            addCriterion("leaderemail between", value1, value2, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andLeaderEmailNotBetween(String value1, String value2) {
            addCriterion("leaderemail not between", value1, value2, "leaderEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailIsNull() {
            addCriterion("groupemail is null");
            return (Criteria) this;
        }

        public Criteria andGroupEmailIsNotNull() {
            addCriterion("groupemail is not null");
            return (Criteria) this;
        }

        public Criteria andGroupEmailEqualTo(String value) {
            addCriterion("groupemail =", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailNotEqualTo(String value) {
            addCriterion("groupemail <>", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailGreaterThan(String value) {
            addCriterion("groupemail >", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailGreaterThanOrEqualTo(String value) {
            addCriterion("groupemail >=", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailLessThan(String value) {
            addCriterion("groupemail <", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailLessThanOrEqualTo(String value) {
            addCriterion("groupemail <=", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailLike(String value) {
            addCriterion("groupemail like", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailNotLike(String value) {
            addCriterion("groupemail not like", value, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailIn(List<String> values) {
            addCriterion("groupemail in", values, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailNotIn(List<String> values) {
            addCriterion("groupemail not in", values, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailBetween(String value1, String value2) {
            addCriterion("groupemail between", value1, value2, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupEmailNotBetween(String value1, String value2) {
            addCriterion("groupemail not between", value1, value2, "groupEmail");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionIsNull() {
            addCriterion("groupfunction is null");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionIsNotNull() {
            addCriterion("groupfunction is not null");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionEqualTo(Integer value) {
            addCriterion("groupfunction =", value, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionNotEqualTo(Integer value) {
            addCriterion("groupfunction <>", value, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionGreaterThan(Integer value) {
            addCriterion("groupfunction >", value, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionGreaterThanOrEqualTo(Integer value) {
            addCriterion("groupfunction >=", value, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionLessThan(Integer value) {
            addCriterion("groupfunction <", value, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionLessThanOrEqualTo(Integer value) {
            addCriterion("groupfunction <=", value, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionIn(List<Integer> values) {
            addCriterion("groupfunction in", values, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionNotIn(List<Integer> values) {
            addCriterion("groupfunction not in", values, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionBetween(Integer value1, Integer value2) {
            addCriterion("groupfunction between", value1, value2, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupFunctionNotBetween(Integer value1, Integer value2) {
            addCriterion("groupfunction not between", value1, value2, "groupFunction");
            return (Criteria) this;
        }

        public Criteria andGroupTypeIsNull() {
            addCriterion("grouptype is null");
            return (Criteria) this;
        }

        public Criteria andGroupTypeIsNotNull() {
            addCriterion("grouptype is not null");
            return (Criteria) this;
        }

        public Criteria andGroupTypeEqualTo(Integer value) {
            addCriterion("grouptype =", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeNotEqualTo(Integer value) {
            addCriterion("grouptype <>", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeGreaterThan(Integer value) {
            addCriterion("grouptype >", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("grouptype >=", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeLessThan(Integer value) {
            addCriterion("grouptype <", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeLessThanOrEqualTo(Integer value) {
            addCriterion("grouptype <=", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeIn(List<Integer> values) {
            addCriterion("grouptype in", values, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeNotIn(List<Integer> values) {
            addCriterion("grouptype not in", values, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeBetween(Integer value1, Integer value2) {
            addCriterion("grouptype between", value1, value2, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("grouptype not between", value1, value2, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupeStateIsNull() {
            addCriterion("groupstate is null");
            return (Criteria) this;
        }

        public Criteria andGroupeStateIsNotNull() {
            addCriterion("groupstate is not null");
            return (Criteria) this;
        }

        public Criteria andGroupeStateEqualTo(Integer value) {
            addCriterion("groupstate =", value, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateNotEqualTo(Integer value) {
            addCriterion("groupstate <>", value, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateGreaterThan(Integer value) {
            addCriterion("groupstate >", value, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("groupstate >=", value, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateLessThan(Integer value) {
            addCriterion("groupstate <", value, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateLessThanOrEqualTo(Integer value) {
            addCriterion("groupstate <=", value, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateIn(List<Integer> values) {
            addCriterion("groupstate in", values, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateNotIn(List<Integer> values) {
            addCriterion("groupstate not in", values, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateBetween(Integer value1, Integer value2) {
            addCriterion("groupstate between", value1, value2, "groupeState");
            return (Criteria) this;
        }

        public Criteria andGroupeStateNotBetween(Integer value1, Integer value2) {
            addCriterion("groupstate not between", value1, value2, "groupeState");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}