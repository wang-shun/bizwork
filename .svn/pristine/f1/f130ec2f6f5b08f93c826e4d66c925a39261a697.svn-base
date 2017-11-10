package com.sogou.bizwork.bo.msg;

import java.util.ArrayList;
import java.util.List;

public class MessageTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageTypeExample() {
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

        public Criteria andMesTypeIdIsNull() {
            addCriterion("mes_type_id is null");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdIsNotNull() {
            addCriterion("mes_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdEqualTo(Integer value) {
            addCriterion("mes_type_id =", value, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdNotEqualTo(Integer value) {
            addCriterion("mes_type_id <>", value, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdGreaterThan(Integer value) {
            addCriterion("mes_type_id >", value, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("mes_type_id >=", value, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdLessThan(Integer value) {
            addCriterion("mes_type_id <", value, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("mes_type_id <=", value, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdIn(List<Integer> values) {
            addCriterion("mes_type_id in", values, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdNotIn(List<Integer> values) {
            addCriterion("mes_type_id not in", values, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("mes_type_id between", value1, value2, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("mes_type_id not between", value1, value2, "mesTypeId");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameIsNull() {
            addCriterion("mes_type_name is null");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameIsNotNull() {
            addCriterion("mes_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameEqualTo(String value) {
            addCriterion("mes_type_name =", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameNotEqualTo(String value) {
            addCriterion("mes_type_name <>", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameGreaterThan(String value) {
            addCriterion("mes_type_name >", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("mes_type_name >=", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameLessThan(String value) {
            addCriterion("mes_type_name <", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameLessThanOrEqualTo(String value) {
            addCriterion("mes_type_name <=", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameLike(String value) {
            addCriterion("mes_type_name like", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameNotLike(String value) {
            addCriterion("mes_type_name not like", value, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameIn(List<String> values) {
            addCriterion("mes_type_name in", values, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameNotIn(List<String> values) {
            addCriterion("mes_type_name not in", values, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameBetween(String value1, String value2) {
            addCriterion("mes_type_name between", value1, value2, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andMesTypeNameNotBetween(String value1, String value2) {
            addCriterion("mes_type_name not between", value1, value2, "mesTypeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNull() {
            addCriterion("parent_mes_type_id is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNotNull() {
            addCriterion("parent_mes_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdEqualTo(Integer value) {
            addCriterion("parent_mes_type_id =", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotEqualTo(Integer value) {
            addCriterion("parent_mes_type_id <>", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThan(Integer value) {
            addCriterion("parent_mes_type_id >", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_mes_type_id >=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThan(Integer value) {
            addCriterion("parent_mes_type_id <", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_mes_type_id <=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIn(List<Integer> values) {
            addCriterion("parent_mes_type_id in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotIn(List<Integer> values) {
            addCriterion("parent_mes_type_id not in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_mes_type_id between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_mes_type_id not between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
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