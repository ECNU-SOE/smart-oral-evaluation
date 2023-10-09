package net.ecnu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/10/10 0:22
 */
public class MistakeAudioDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MistakeAudioDOExample() {
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

        public Criteria andMistakeIdIsNull() {
            addCriterion("mistake_id is null");
            return (Criteria) this;
        }

        public Criteria andMistakeIdIsNotNull() {
            addCriterion("mistake_id is not null");
            return (Criteria) this;
        }

        public Criteria andMistakeIdEqualTo(Long value) {
            addCriterion("mistake_id =", value, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdNotEqualTo(Long value) {
            addCriterion("mistake_id <>", value, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdGreaterThan(Long value) {
            addCriterion("mistake_id >", value, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("mistake_id >=", value, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdLessThan(Long value) {
            addCriterion("mistake_id <", value, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdLessThanOrEqualTo(Long value) {
            addCriterion("mistake_id <=", value, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdIn(List<Long> values) {
            addCriterion("mistake_id in", values, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdNotIn(List<Long> values) {
            addCriterion("mistake_id not in", values, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdBetween(Long value1, Long value2) {
            addCriterion("mistake_id between", value1, value2, "mistakeId");
            return (Criteria) this;
        }

        public Criteria andMistakeIdNotBetween(Long value1, Long value2) {
            addCriterion("mistake_id not between", value1, value2, "mistakeId");
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

        public Criteria andCpsrcdIdIsNull() {
            addCriterion("cpsrcd_id is null");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdIsNotNull() {
            addCriterion("cpsrcd_id is not null");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdEqualTo(String value) {
            addCriterion("cpsrcd_id =", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdNotEqualTo(String value) {
            addCriterion("cpsrcd_id <>", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdGreaterThan(String value) {
            addCriterion("cpsrcd_id >", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdGreaterThanOrEqualTo(String value) {
            addCriterion("cpsrcd_id >=", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdLessThan(String value) {
            addCriterion("cpsrcd_id <", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdLessThanOrEqualTo(String value) {
            addCriterion("cpsrcd_id <=", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdLike(String value) {
            addCriterion("cpsrcd_id like", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdNotLike(String value) {
            addCriterion("cpsrcd_id not like", value, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdIn(List<String> values) {
            addCriterion("cpsrcd_id in", values, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdNotIn(List<String> values) {
            addCriterion("cpsrcd_id not in", values, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdBetween(String value1, String value2) {
            addCriterion("cpsrcd_id between", value1, value2, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andCpsrcdIdNotBetween(String value1, String value2) {
            addCriterion("cpsrcd_id not between", value1, value2, "cpsrcdId");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeIsNull() {
            addCriterion("mistake_type is null");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeIsNotNull() {
            addCriterion("mistake_type is not null");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeEqualTo(Integer value) {
            addCriterion("mistake_type =", value, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeNotEqualTo(Integer value) {
            addCriterion("mistake_type <>", value, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeGreaterThan(Integer value) {
            addCriterion("mistake_type >", value, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("mistake_type >=", value, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeLessThan(Integer value) {
            addCriterion("mistake_type <", value, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("mistake_type <=", value, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeIn(List<Integer> values) {
            addCriterion("mistake_type in", values, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeNotIn(List<Integer> values) {
            addCriterion("mistake_type not in", values, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeBetween(Integer value1, Integer value2) {
            addCriterion("mistake_type between", value1, value2, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andMistakeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("mistake_type not between", value1, value2, "mistakeType");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andErrorSumIsNull() {
            addCriterion("error_sum is null");
            return (Criteria) this;
        }

        public Criteria andErrorSumIsNotNull() {
            addCriterion("error_sum is not null");
            return (Criteria) this;
        }

        public Criteria andErrorSumEqualTo(Integer value) {
            addCriterion("error_sum =", value, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumNotEqualTo(Integer value) {
            addCriterion("error_sum <>", value, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumGreaterThan(Integer value) {
            addCriterion("error_sum >", value, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("error_sum >=", value, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumLessThan(Integer value) {
            addCriterion("error_sum <", value, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumLessThanOrEqualTo(Integer value) {
            addCriterion("error_sum <=", value, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumIn(List<Integer> values) {
            addCriterion("error_sum in", values, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumNotIn(List<Integer> values) {
            addCriterion("error_sum not in", values, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumBetween(Integer value1, Integer value2) {
            addCriterion("error_sum between", value1, value2, "errorSum");
            return (Criteria) this;
        }

        public Criteria andErrorSumNotBetween(Integer value1, Integer value2) {
            addCriterion("error_sum not between", value1, value2, "errorSum");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(Boolean value) {
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(Boolean value) {
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(Boolean value) {
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(Boolean value) {
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(Boolean value) {
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<Boolean> values) {
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<Boolean> values) {
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flg not between", value1, value2, "delFlg");
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

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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