package jrd.graduationproject.shoppingplatform.pojo.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopCarExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShopCarExample() {
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
        protected List<Criterion> userCriteria;

        protected List<Criterion> wareCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            userCriteria = new ArrayList<Criterion>();
            wareCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getUserCriteria() {
            return userCriteria;
        }

        protected void addUserCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            userCriteria.add(new Criterion(condition, value, "jrd.graduationproject.shoppingplatform.config.jdbc.DefaultTypeHandler"));
            allCriteria = null;
        }

        protected void addUserCriterion(String condition, User value1, User value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            userCriteria.add(new Criterion(condition, value1, value2, "jrd.graduationproject.shoppingplatform.config.jdbc.DefaultTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getWareCriteria() {
            return wareCriteria;
        }

        protected void addWareCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            wareCriteria.add(new Criterion(condition, value, "jrd.graduationproject.shoppingplatform.config.jdbc.DefaultTypeHandler"));
            allCriteria = null;
        }

        protected void addWareCriterion(String condition, Ware value1, Ware value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            wareCriteria.add(new Criterion(condition, value1, value2, "jrd.graduationproject.shoppingplatform.config.jdbc.DefaultTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || userCriteria.size() > 0
                || wareCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(userCriteria);
                allCriteria.addAll(wareCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
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

        public Criteria andCreatedateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andWarenumIsNull() {
            addCriterion("warenum is null");
            return (Criteria) this;
        }

        public Criteria andWarenumIsNotNull() {
            addCriterion("warenum is not null");
            return (Criteria) this;
        }

        public Criteria andWarenumEqualTo(Integer value) {
            addCriterion("warenum =", value, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumNotEqualTo(Integer value) {
            addCriterion("warenum <>", value, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumGreaterThan(Integer value) {
            addCriterion("warenum >", value, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumGreaterThanOrEqualTo(Integer value) {
            addCriterion("warenum >=", value, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumLessThan(Integer value) {
            addCriterion("warenum <", value, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumLessThanOrEqualTo(Integer value) {
            addCriterion("warenum <=", value, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumIn(List<Integer> values) {
            addCriterion("warenum in", values, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumNotIn(List<Integer> values) {
            addCriterion("warenum not in", values, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumBetween(Integer value1, Integer value2) {
            addCriterion("warenum between", value1, value2, "warenum");
            return (Criteria) this;
        }

        public Criteria andWarenumNotBetween(Integer value1, Integer value2) {
            addCriterion("warenum not between", value1, value2, "warenum");
            return (Criteria) this;
        }

        public Criteria andUserIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUserIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUserEqualTo(User value) {
            addUserCriterion("userid =", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotEqualTo(User value) {
            addUserCriterion("userid <>", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThan(User value) {
            addUserCriterion("userid >", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThanOrEqualTo(User value) {
            addUserCriterion("userid >=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThan(User value) {
            addUserCriterion("userid <", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThanOrEqualTo(User value) {
            addUserCriterion("userid <=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLike(User value) {
            addUserCriterion("userid like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotLike(User value) {
            addUserCriterion("userid not like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserIn(List<User> values) {
            addUserCriterion("userid in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotIn(List<User> values) {
            addUserCriterion("userid not in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserBetween(User value1, User value2) {
            addUserCriterion("userid between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotBetween(User value1, User value2) {
            addUserCriterion("userid not between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andWareIsNull() {
            addCriterion("wareid is null");
            return (Criteria) this;
        }

        public Criteria andWareIsNotNull() {
            addCriterion("wareid is not null");
            return (Criteria) this;
        }

        public Criteria andWareEqualTo(Ware value) {
            addWareCriterion("wareid =", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareNotEqualTo(Ware value) {
            addWareCriterion("wareid <>", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareGreaterThan(Ware value) {
            addWareCriterion("wareid >", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareGreaterThanOrEqualTo(Ware value) {
            addWareCriterion("wareid >=", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareLessThan(Ware value) {
            addWareCriterion("wareid <", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareLessThanOrEqualTo(Ware value) {
            addWareCriterion("wareid <=", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareLike(Ware value) {
            addWareCriterion("wareid like", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareNotLike(Ware value) {
            addWareCriterion("wareid not like", value, "ware");
            return (Criteria) this;
        }

        public Criteria andWareIn(List<Ware> values) {
            addWareCriterion("wareid in", values, "ware");
            return (Criteria) this;
        }

        public Criteria andWareNotIn(List<Ware> values) {
            addWareCriterion("wareid not in", values, "ware");
            return (Criteria) this;
        }

        public Criteria andWareBetween(Ware value1, Ware value2) {
            addWareCriterion("wareid between", value1, value2, "ware");
            return (Criteria) this;
        }

        public Criteria andWareNotBetween(Ware value1, Ware value2) {
            addWareCriterion("wareid not between", value1, value2, "ware");
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