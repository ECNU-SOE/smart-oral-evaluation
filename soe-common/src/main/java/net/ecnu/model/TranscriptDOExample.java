package net.ecnu.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description 
 * @author Joshua
 * @date 2023/12/10 17:22
 */
public class TranscriptDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TranscriptDOExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdIsNull() {
            addCriterion("cpsgrp_id is null");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdIsNotNull() {
            addCriterion("cpsgrp_id is not null");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdEqualTo(String value) {
            addCriterion("cpsgrp_id =", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdNotEqualTo(String value) {
            addCriterion("cpsgrp_id <>", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdGreaterThan(String value) {
            addCriterion("cpsgrp_id >", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdGreaterThanOrEqualTo(String value) {
            addCriterion("cpsgrp_id >=", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdLessThan(String value) {
            addCriterion("cpsgrp_id <", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdLessThanOrEqualTo(String value) {
            addCriterion("cpsgrp_id <=", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdLike(String value) {
            addCriterion("cpsgrp_id like", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdNotLike(String value) {
            addCriterion("cpsgrp_id not like", value, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdIn(List<String> values) {
            addCriterion("cpsgrp_id in", values, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdNotIn(List<String> values) {
            addCriterion("cpsgrp_id not in", values, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdBetween(String value1, String value2) {
            addCriterion("cpsgrp_id between", value1, value2, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andCpsgrpIdNotBetween(String value1, String value2) {
            addCriterion("cpsgrp_id not between", value1, value2, "cpsgrpId");
            return (Criteria) this;
        }

        public Criteria andRespondentIsNull() {
            addCriterion("respondent is null");
            return (Criteria) this;
        }

        public Criteria andRespondentIsNotNull() {
            addCriterion("respondent is not null");
            return (Criteria) this;
        }

        public Criteria andRespondentEqualTo(String value) {
            addCriterion("respondent =", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentNotEqualTo(String value) {
            addCriterion("respondent <>", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentGreaterThan(String value) {
            addCriterion("respondent >", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentGreaterThanOrEqualTo(String value) {
            addCriterion("respondent >=", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentLessThan(String value) {
            addCriterion("respondent <", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentLessThanOrEqualTo(String value) {
            addCriterion("respondent <=", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentLike(String value) {
            addCriterion("respondent like", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentNotLike(String value) {
            addCriterion("respondent not like", value, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentIn(List<String> values) {
            addCriterion("respondent in", values, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentNotIn(List<String> values) {
            addCriterion("respondent not in", values, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentBetween(String value1, String value2) {
            addCriterion("respondent between", value1, value2, "respondent");
            return (Criteria) this;
        }

        public Criteria andRespondentNotBetween(String value1, String value2) {
            addCriterion("respondent not between", value1, value2, "respondent");
            return (Criteria) this;
        }

        public Criteria andSystemScoreIsNull() {
            addCriterion("system_score is null");
            return (Criteria) this;
        }

        public Criteria andSystemScoreIsNotNull() {
            addCriterion("system_score is not null");
            return (Criteria) this;
        }

        public Criteria andSystemScoreEqualTo(BigDecimal value) {
            addCriterion("system_score =", value, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreNotEqualTo(BigDecimal value) {
            addCriterion("system_score <>", value, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreGreaterThan(BigDecimal value) {
            addCriterion("system_score >", value, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("system_score >=", value, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreLessThan(BigDecimal value) {
            addCriterion("system_score <", value, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("system_score <=", value, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreIn(List<BigDecimal> values) {
            addCriterion("system_score in", values, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreNotIn(List<BigDecimal> values) {
            addCriterion("system_score not in", values, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("system_score between", value1, value2, "systemScore");
            return (Criteria) this;
        }

        public Criteria andSystemScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("system_score not between", value1, value2, "systemScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreIsNull() {
            addCriterion("manual_score is null");
            return (Criteria) this;
        }

        public Criteria andManualScoreIsNotNull() {
            addCriterion("manual_score is not null");
            return (Criteria) this;
        }

        public Criteria andManualScoreEqualTo(BigDecimal value) {
            addCriterion("manual_score =", value, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreNotEqualTo(BigDecimal value) {
            addCriterion("manual_score <>", value, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreGreaterThan(BigDecimal value) {
            addCriterion("manual_score >", value, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("manual_score >=", value, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreLessThan(BigDecimal value) {
            addCriterion("manual_score <", value, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("manual_score <=", value, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreIn(List<BigDecimal> values) {
            addCriterion("manual_score in", values, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreNotIn(List<BigDecimal> values) {
            addCriterion("manual_score not in", values, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manual_score between", value1, value2, "manualScore");
            return (Criteria) this;
        }

        public Criteria andManualScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manual_score not between", value1, value2, "manualScore");
            return (Criteria) this;
        }

        public Criteria andMarkTimeIsNull() {
            addCriterion("mark_time is null");
            return (Criteria) this;
        }

        public Criteria andMarkTimeIsNotNull() {
            addCriterion("mark_time is not null");
            return (Criteria) this;
        }

        public Criteria andMarkTimeEqualTo(Date value) {
            addCriterion("mark_time =", value, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeNotEqualTo(Date value) {
            addCriterion("mark_time <>", value, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeGreaterThan(Date value) {
            addCriterion("mark_time >", value, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("mark_time >=", value, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeLessThan(Date value) {
            addCriterion("mark_time <", value, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeLessThanOrEqualTo(Date value) {
            addCriterion("mark_time <=", value, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeIn(List<Date> values) {
            addCriterion("mark_time in", values, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeNotIn(List<Date> values) {
            addCriterion("mark_time not in", values, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeBetween(Date value1, Date value2) {
            addCriterion("mark_time between", value1, value2, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkTimeNotBetween(Date value1, Date value2) {
            addCriterion("mark_time not between", value1, value2, "markTime");
            return (Criteria) this;
        }

        public Criteria andMarkUserIsNull() {
            addCriterion("mark_user is null");
            return (Criteria) this;
        }

        public Criteria andMarkUserIsNotNull() {
            addCriterion("mark_user is not null");
            return (Criteria) this;
        }

        public Criteria andMarkUserEqualTo(String value) {
            addCriterion("mark_user =", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserNotEqualTo(String value) {
            addCriterion("mark_user <>", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserGreaterThan(String value) {
            addCriterion("mark_user >", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserGreaterThanOrEqualTo(String value) {
            addCriterion("mark_user >=", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserLessThan(String value) {
            addCriterion("mark_user <", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserLessThanOrEqualTo(String value) {
            addCriterion("mark_user <=", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserLike(String value) {
            addCriterion("mark_user like", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserNotLike(String value) {
            addCriterion("mark_user not like", value, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserIn(List<String> values) {
            addCriterion("mark_user in", values, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserNotIn(List<String> values) {
            addCriterion("mark_user not in", values, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserBetween(String value1, String value2) {
            addCriterion("mark_user between", value1, value2, "markUser");
            return (Criteria) this;
        }

        public Criteria andMarkUserNotBetween(String value1, String value2) {
            addCriterion("mark_user not between", value1, value2, "markUser");
            return (Criteria) this;
        }

        public Criteria andResJsonIsNull() {
            addCriterion("res_json is null");
            return (Criteria) this;
        }

        public Criteria andResJsonIsNotNull() {
            addCriterion("res_json is not null");
            return (Criteria) this;
        }

        public Criteria andResJsonEqualTo(String value) {
            addCriterion("res_json =", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonNotEqualTo(String value) {
            addCriterion("res_json <>", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonGreaterThan(String value) {
            addCriterion("res_json >", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonGreaterThanOrEqualTo(String value) {
            addCriterion("res_json >=", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonLessThan(String value) {
            addCriterion("res_json <", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonLessThanOrEqualTo(String value) {
            addCriterion("res_json <=", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonLike(String value) {
            addCriterion("res_json like", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonNotLike(String value) {
            addCriterion("res_json not like", value, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonIn(List<String> values) {
            addCriterion("res_json in", values, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonNotIn(List<String> values) {
            addCriterion("res_json not in", values, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonBetween(String value1, String value2) {
            addCriterion("res_json between", value1, value2, "resJson");
            return (Criteria) this;
        }

        public Criteria andResJsonNotBetween(String value1, String value2) {
            addCriterion("res_json not between", value1, value2, "resJson");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
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