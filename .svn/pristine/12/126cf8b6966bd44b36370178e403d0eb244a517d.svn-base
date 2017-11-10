package com.sogou.bizwork.bo.msg;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageMesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageMesExample() {
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

        public Criteria andMesIdIsNull() {
            addCriterion("mes_id is null");
            return (Criteria) this;
        }

        public Criteria andMesIdIsNotNull() {
            addCriterion("mes_id is not null");
            return (Criteria) this;
        }

        public Criteria andMesIdEqualTo(Long value) {
            addCriterion("mes_id =", value, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdNotEqualTo(Long value) {
            addCriterion("mes_id <>", value, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdGreaterThan(Long value) {
            addCriterion("mes_id >", value, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdGreaterThanOrEqualTo(Long value) {
            addCriterion("mes_id >=", value, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdLessThan(Long value) {
            addCriterion("mes_id <", value, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdLessThanOrEqualTo(Long value) {
            addCriterion("mes_id <=", value, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdIn(List<Long> values) {
            addCriterion("mes_id in", values, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdNotIn(List<Long> values) {
            addCriterion("mes_id not in", values, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdBetween(Long value1, Long value2) {
            addCriterion("mes_id between", value1, value2, "mesId");
            return (Criteria) this;
        }

        public Criteria andMesIdNotBetween(Long value1, Long value2) {
            addCriterion("mes_id not between", value1, value2, "mesId");
            return (Criteria) this;
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andPicIsNull() {
            addCriterion("pic is null");
            return (Criteria) this;
        }

        public Criteria andPicIsNotNull() {
            addCriterion("pic is not null");
            return (Criteria) this;
        }

        public Criteria andPicEqualTo(String value) {
            addCriterion("pic =", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotEqualTo(String value) {
            addCriterion("pic <>", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicGreaterThan(String value) {
            addCriterion("pic >", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicGreaterThanOrEqualTo(String value) {
            addCriterion("pic >=", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLessThan(String value) {
            addCriterion("pic <", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLessThanOrEqualTo(String value) {
            addCriterion("pic <=", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicLike(String value) {
            addCriterion("pic like", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotLike(String value) {
            addCriterion("pic not like", value, "pic");
            return (Criteria) this;
        }

        public Criteria andPicIn(List<String> values) {
            addCriterion("pic in", values, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotIn(List<String> values) {
            addCriterion("pic not in", values, "pic");
            return (Criteria) this;
        }

        public Criteria andPicBetween(String value1, String value2) {
            addCriterion("pic between", value1, value2, "pic");
            return (Criteria) this;
        }

        public Criteria andPicNotBetween(String value1, String value2) {
            addCriterion("pic not between", value1, value2, "pic");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andPlatFormIsNull() {
            addCriterion("platform is null");
            return (Criteria) this;
        }

        public Criteria andPlatFormIsNotNull() {
            addCriterion("platform is not null");
            return (Criteria) this;
        }

        public Criteria andPlatFormEqualTo(Integer value) {
            addCriterion("platform =", value, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormNotEqualTo(Integer value) {
            addCriterion("platform <>", value, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormGreaterThan(Integer value) {
            addCriterion("platform >", value, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormGreaterThanOrEqualTo(Integer value) {
            addCriterion("platform >=", value, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormLessThan(Integer value) {
            addCriterion("platform <", value, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormLessThanOrEqualTo(Integer value) {
            addCriterion("platform <=", value, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormIn(List<Integer> values) {
            addCriterion("platform in", values, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormNotIn(List<Integer> values) {
            addCriterion("platform not in", values, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormBetween(Integer value1, Integer value2) {
            addCriterion("platform between", value1, value2, "platForm");
            return (Criteria) this;
        }

        public Criteria andPlatFormNotBetween(Integer value1, Integer value2) {
            addCriterion("platform not between", value1, value2, "platForm");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Timestamp value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Timestamp value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Timestamp value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Timestamp value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Timestamp> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Timestamp> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Integer value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Integer value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Integer value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Integer value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Integer> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Integer> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Integer value1, Integer value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_id not between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andIfRecallIsNull() {
            addCriterion("if_recall is null");
            return (Criteria) this;
        }

        public Criteria andIfRecallIsNotNull() {
            addCriterion("if_recall is not null");
            return (Criteria) this;
        }

        public Criteria andIfRecallEqualTo(Integer value) {
            addCriterion("if_recall =", value, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallNotEqualTo(Integer value) {
            addCriterion("if_recall <>", value, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallGreaterThan(Integer value) {
            addCriterion("if_recall >", value, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallGreaterThanOrEqualTo(Integer value) {
            addCriterion("if_recall >=", value, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallLessThan(Integer value) {
            addCriterion("if_recall <", value, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallLessThanOrEqualTo(Integer value) {
            addCriterion("if_recall <=", value, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallIn(List<Integer> values) {
            addCriterion("if_recall in", values, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallNotIn(List<Integer> values) {
            addCriterion("if_recall not in", values, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallBetween(Integer value1, Integer value2) {
            addCriterion("if_recall between", value1, value2, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andIfRecallNotBetween(Integer value1, Integer value2) {
            addCriterion("if_recall not between", value1, value2, "ifRecall");
            return (Criteria) this;
        }

        public Criteria andReceiversIsNull() {
            addCriterion("receivers is null");
            return (Criteria) this;
        }

        public Criteria andReceiversIsNotNull() {
            addCriterion("receivers is not null");
            return (Criteria) this;
        }

        public Criteria andReceiversEqualTo(String value) {
            addCriterion("receivers =", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotEqualTo(String value) {
            addCriterion("receivers <>", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversGreaterThan(String value) {
            addCriterion("receivers >", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversGreaterThanOrEqualTo(String value) {
            addCriterion("receivers >=", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversLessThan(String value) {
            addCriterion("receivers <", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversLessThanOrEqualTo(String value) {
            addCriterion("receivers <=", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversLike(String value) {
            addCriterion("receivers like", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotLike(String value) {
            addCriterion("receivers not like", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversIn(List<String> values) {
            addCriterion("receivers in", values, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotIn(List<String> values) {
            addCriterion("receivers not in", values, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversBetween(String value1, String value2) {
            addCriterion("receivers between", value1, value2, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotBetween(String value1, String value2) {
            addCriterion("receivers not between", value1, value2, "receivers");
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