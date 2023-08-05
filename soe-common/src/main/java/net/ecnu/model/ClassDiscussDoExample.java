package net.ecnu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/8/5 9:31
 */
public class ClassDiscussDoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClassDiscussDoExample() {
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

        public Criteria andDiscussIdIsNull() {
            addCriterion("discuss_id is null");
            return (Criteria) this;
        }

        public Criteria andDiscussIdIsNotNull() {
            addCriterion("discuss_id is not null");
            return (Criteria) this;
        }

        public Criteria andDiscussIdEqualTo(Long value) {
            addCriterion("discuss_id =", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdNotEqualTo(Long value) {
            addCriterion("discuss_id <>", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdGreaterThan(Long value) {
            addCriterion("discuss_id >", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdGreaterThanOrEqualTo(Long value) {
            addCriterion("discuss_id >=", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdLessThan(Long value) {
            addCriterion("discuss_id <", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdLessThanOrEqualTo(Long value) {
            addCriterion("discuss_id <=", value, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdIn(List<Long> values) {
            addCriterion("discuss_id in", values, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdNotIn(List<Long> values) {
            addCriterion("discuss_id not in", values, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdBetween(Long value1, Long value2) {
            addCriterion("discuss_id between", value1, value2, "discussId");
            return (Criteria) this;
        }

        public Criteria andDiscussIdNotBetween(Long value1, Long value2) {
            addCriterion("discuss_id not between", value1, value2, "discussId");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(String value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(String value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(String value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(String value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(String value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(String value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLike(String value) {
            addCriterion("class_id like", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotLike(String value) {
            addCriterion("class_id not like", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<String> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<String> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(String value1, String value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(String value1, String value2) {
            addCriterion("class_id not between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNull() {
            addCriterion("publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(String value) {
            addCriterion("publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(String value) {
            addCriterion("publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(String value) {
            addCriterion("publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(String value) {
            addCriterion("publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(String value) {
            addCriterion("publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(String value) {
            addCriterion("publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLike(String value) {
            addCriterion("publisher like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotLike(String value) {
            addCriterion("publisher not like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<String> values) {
            addCriterion("publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<String> values) {
            addCriterion("publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(String value1, String value2) {
            addCriterion("publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(String value1, String value2) {
            addCriterion("publisher not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleIsNull() {
            addCriterion("discuss_title is null");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleIsNotNull() {
            addCriterion("discuss_title is not null");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleEqualTo(String value) {
            addCriterion("discuss_title =", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleNotEqualTo(String value) {
            addCriterion("discuss_title <>", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleGreaterThan(String value) {
            addCriterion("discuss_title >", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleGreaterThanOrEqualTo(String value) {
            addCriterion("discuss_title >=", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleLessThan(String value) {
            addCriterion("discuss_title <", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleLessThanOrEqualTo(String value) {
            addCriterion("discuss_title <=", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleLike(String value) {
            addCriterion("discuss_title like", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleNotLike(String value) {
            addCriterion("discuss_title not like", value, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleIn(List<String> values) {
            addCriterion("discuss_title in", values, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleNotIn(List<String> values) {
            addCriterion("discuss_title not in", values, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleBetween(String value1, String value2) {
            addCriterion("discuss_title between", value1, value2, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussTitleNotBetween(String value1, String value2) {
            addCriterion("discuss_title not between", value1, value2, "discussTitle");
            return (Criteria) this;
        }

        public Criteria andDiscussContentIsNull() {
            addCriterion("discuss_content is null");
            return (Criteria) this;
        }

        public Criteria andDiscussContentIsNotNull() {
            addCriterion("discuss_content is not null");
            return (Criteria) this;
        }

        public Criteria andDiscussContentEqualTo(String value) {
            addCriterion("discuss_content =", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentNotEqualTo(String value) {
            addCriterion("discuss_content <>", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentGreaterThan(String value) {
            addCriterion("discuss_content >", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentGreaterThanOrEqualTo(String value) {
            addCriterion("discuss_content >=", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentLessThan(String value) {
            addCriterion("discuss_content <", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentLessThanOrEqualTo(String value) {
            addCriterion("discuss_content <=", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentLike(String value) {
            addCriterion("discuss_content like", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentNotLike(String value) {
            addCriterion("discuss_content not like", value, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentIn(List<String> values) {
            addCriterion("discuss_content in", values, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentNotIn(List<String> values) {
            addCriterion("discuss_content not in", values, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentBetween(String value1, String value2) {
            addCriterion("discuss_content between", value1, value2, "discussContent");
            return (Criteria) this;
        }

        public Criteria andDiscussContentNotBetween(String value1, String value2) {
            addCriterion("discuss_content not between", value1, value2, "discussContent");
            return (Criteria) this;
        }

        public Criteria andForwardIdIsNull() {
            addCriterion("forward_id is null");
            return (Criteria) this;
        }

        public Criteria andForwardIdIsNotNull() {
            addCriterion("forward_id is not null");
            return (Criteria) this;
        }

        public Criteria andForwardIdEqualTo(Long value) {
            addCriterion("forward_id =", value, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdNotEqualTo(Long value) {
            addCriterion("forward_id <>", value, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdGreaterThan(Long value) {
            addCriterion("forward_id >", value, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdGreaterThanOrEqualTo(Long value) {
            addCriterion("forward_id >=", value, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdLessThan(Long value) {
            addCriterion("forward_id <", value, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdLessThanOrEqualTo(Long value) {
            addCriterion("forward_id <=", value, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdIn(List<Long> values) {
            addCriterion("forward_id in", values, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdNotIn(List<Long> values) {
            addCriterion("forward_id not in", values, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdBetween(Long value1, Long value2) {
            addCriterion("forward_id between", value1, value2, "forwardId");
            return (Criteria) this;
        }

        public Criteria andForwardIdNotBetween(Long value1, Long value2) {
            addCriterion("forward_id not between", value1, value2, "forwardId");
            return (Criteria) this;
        }

        public Criteria andLikeCountIsNull() {
            addCriterion("like_count is null");
            return (Criteria) this;
        }

        public Criteria andLikeCountIsNotNull() {
            addCriterion("like_count is not null");
            return (Criteria) this;
        }

        public Criteria andLikeCountEqualTo(Integer value) {
            addCriterion("like_count =", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotEqualTo(Integer value) {
            addCriterion("like_count <>", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountGreaterThan(Integer value) {
            addCriterion("like_count >", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("like_count >=", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountLessThan(Integer value) {
            addCriterion("like_count <", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountLessThanOrEqualTo(Integer value) {
            addCriterion("like_count <=", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountIn(List<Integer> values) {
            addCriterion("like_count in", values, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotIn(List<Integer> values) {
            addCriterion("like_count not in", values, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountBetween(Integer value1, Integer value2) {
            addCriterion("like_count between", value1, value2, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("like_count not between", value1, value2, "likeCount");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNull() {
            addCriterion("is_leaf is null");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNotNull() {
            addCriterion("is_leaf is not null");
            return (Criteria) this;
        }

        public Criteria andIsLeafEqualTo(Boolean value) {
            addCriterion("is_leaf =", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotEqualTo(Boolean value) {
            addCriterion("is_leaf <>", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThan(Boolean value) {
            addCriterion("is_leaf >", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_leaf >=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThan(Boolean value) {
            addCriterion("is_leaf <", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThanOrEqualTo(Boolean value) {
            addCriterion("is_leaf <=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafIn(List<Boolean> values) {
            addCriterion("is_leaf in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotIn(List<Boolean> values) {
            addCriterion("is_leaf not in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafBetween(Boolean value1, Boolean value2) {
            addCriterion("is_leaf between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_leaf not between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIsNull() {
            addCriterion("release_time is null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIsNotNull() {
            addCriterion("release_time is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeEqualTo(Date value) {
            addCriterion("release_time =", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotEqualTo(Date value) {
            addCriterion("release_time <>", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeGreaterThan(Date value) {
            addCriterion("release_time >", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("release_time >=", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeLessThan(Date value) {
            addCriterion("release_time <", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeLessThanOrEqualTo(Date value) {
            addCriterion("release_time <=", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIn(List<Date> values) {
            addCriterion("release_time in", values, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotIn(List<Date> values) {
            addCriterion("release_time not in", values, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeBetween(Date value1, Date value2) {
            addCriterion("release_time between", value1, value2, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotBetween(Date value1, Date value2) {
            addCriterion("release_time not between", value1, value2, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeIsNull() {
            addCriterion("sort_time is null");
            return (Criteria) this;
        }

        public Criteria andSortTimeIsNotNull() {
            addCriterion("sort_time is not null");
            return (Criteria) this;
        }

        public Criteria andSortTimeEqualTo(Date value) {
            addCriterion("sort_time =", value, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeNotEqualTo(Date value) {
            addCriterion("sort_time <>", value, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeGreaterThan(Date value) {
            addCriterion("sort_time >", value, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sort_time >=", value, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeLessThan(Date value) {
            addCriterion("sort_time <", value, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeLessThanOrEqualTo(Date value) {
            addCriterion("sort_time <=", value, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeIn(List<Date> values) {
            addCriterion("sort_time in", values, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeNotIn(List<Date> values) {
            addCriterion("sort_time not in", values, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeBetween(Date value1, Date value2) {
            addCriterion("sort_time between", value1, value2, "sortTime");
            return (Criteria) this;
        }

        public Criteria andSortTimeNotBetween(Date value1, Date value2) {
            addCriterion("sort_time not between", value1, value2, "sortTime");
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

        public Criteria andReplyNumberIsNull() {
            addCriterion("reply_number is null");
            return (Criteria) this;
        }

        public Criteria andReplyNumberIsNotNull() {
            addCriterion("reply_number is not null");
            return (Criteria) this;
        }

        public Criteria andReplyNumberEqualTo(Integer value) {
            addCriterion("reply_number =", value, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberNotEqualTo(Integer value) {
            addCriterion("reply_number <>", value, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberGreaterThan(Integer value) {
            addCriterion("reply_number >", value, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("reply_number >=", value, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberLessThan(Integer value) {
            addCriterion("reply_number <", value, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberLessThanOrEqualTo(Integer value) {
            addCriterion("reply_number <=", value, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberIn(List<Integer> values) {
            addCriterion("reply_number in", values, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberNotIn(List<Integer> values) {
            addCriterion("reply_number not in", values, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberBetween(Integer value1, Integer value2) {
            addCriterion("reply_number between", value1, value2, "replyNumber");
            return (Criteria) this;
        }

        public Criteria andReplyNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("reply_number not between", value1, value2, "replyNumber");
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