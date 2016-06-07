package ${MODEL_PACKAGE}.example;

import java.util.ArrayList;
import java.util.List;

public class ${CLASS_NAME} {
 
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ${CLASS_NAME}() {
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

		<#list sequence as item>

        public Criteria and${item.upperName}IsNull() {
            addCriterion("${item.lowerName} is null");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}IsNotNull() {
            addCriterion("${item.lowerName} is not null");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}EqualTo(${item.type} value) {
            addCriterion("${item.lowerName} =", value, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}NotEqualTo(${item.type} value) {
            addCriterion("${item.lowerName} <>", value, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}GreaterThan(${item.type} value) {
            addCriterion("${item.lowerName} >", value, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}GreaterThanOrEqualTo(${item.type} value) {
            addCriterion("${item.lowerName} >=", value, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}LessThan(${item.type} value) {
            addCriterion("${item.lowerName} <", value, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}LessThanOrEqualTo(${item.type} value) {
            addCriterion("${item.lowerName} <=", value, "${item.lowerName}");
            return (Criteria) this;
        }
        
        <#if !item.primitive>
        public Criteria and${item.upperName}Like(${item.type} value) {
            addCriterion("${item.lowerName} like", value, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}NotLike(${item.type} value) {
            addCriterion("${item.lowerName} not like", value, "${item.lowerName}");
            return (Criteria) this;
        }
        </#if>
        
        public Criteria and${item.upperName}In(List<${item.type}> values) {
            addCriterion("${item.lowerName} in", values, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}NotIn(List<${item.type}> values) {
            addCriterion("${item.lowerName} not in", values, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}Between(${item.type} value1,${item.type} value2) {
            addCriterion("${item.lowerName} between", value1, value2, "${item.lowerName}");
            return (Criteria) this;
        }

        public Criteria and${item.upperName}NotBetween(${item.type} value1,${item.type} value2) {
            addCriterion("${item.lowerName} not between", value1, value2, "${item.lowerName}");
            return (Criteria) this;
        }
		</#list>
      
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