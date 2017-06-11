package net.skeyurt.lit.jdbc.sta;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.jdbc.enums.Operator;
import net.skeyurt.lit.jdbc.spi.expr.LeftParenthesis;
import net.skeyurt.lit.jdbc.spi.expr.RightParenthesis;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/6/4 8:51
 * version $Id: AbstractCondition.java, v 0.1 Exp $
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCondition<T extends Condition<T>> extends AbstractStatement implements Condition<T> {

    protected Expression where;

    AbstractCondition(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public T idCondition(Object value) {
        return idCondition(Operator.EQ, value);
    }

    @Override
    public T idCondition(Operator operator, Object... values) {
        return and(tableInfo.getPkField(), operator, values);
    }

    @Override
    public T where(String fieldName, Object value) {
        addExpr(fieldName, Operator.EQ, true, false, value);
        return (T) this;
    }

    @Override
    public T where(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, false, values);
        return (T) this;
    }

    @Override
    public T and(String fieldName, Object value) {
        this.and(fieldName, Operator.EQ, value);
        return (T) this;
    }

    @Override
    public T and(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, false, values);
        return (T) this;
    }

    @Override
    public T andWithBracket(String fieldName, Object value) {
        this.andWithBracket(fieldName, Operator.EQ, value);
        return (T) this;
    }

    @Override
    public T andWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, true, values);
        return (T) this;
    }

    @Override
    public T or(String fieldName, Object value) {
        this.or(fieldName, Operator.EQ, value);
        return (T) this;
    }

    @Override
    public T or(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, false, values);
        return (T) this;
    }

    @Override
    public T orWithBracket(String fieldName, Object value) {
        this.orWithBracket(fieldName, Operator.EQ, value);
        return (T) this;
    }

    @Override
    public T orWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, true, values);
        return (T) this;
    }

    @Override
    public T end() {
        if (where != null) {
            where = new RightParenthesis(where);
        }
        return (T) this;
    }

    @Override
    public T beanCondition(Object bean) {
        for (String field : tableInfo.getFieldColumnMap().keySet()) {
            Object value = BeanUtils.invokeReaderMethod(bean, field);
            if (value != null && (!(value instanceof String) || StringUtils.isNotBlank((String) value))) {
                and(field, value);
            }
        }

        return (T) this;
    }


    private void addExpr(String fieldName, Operator operator, boolean isAnd, boolean useBracket, Object... values) {
        Expression expression = getExpression(fieldName, operator, values);
        if (expression != null) {
            if (useBracket) {
                expression = new LeftParenthesis(expression);
            }
            where = where == null ? expression :
                    isAnd ? new AndExpression(where, expression) :
                            new OrExpression(where, expression);
        }
    }


    protected Expression getExpression(String fieldName, Operator operator, Object... values) {

        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }

        Column column = new Column(table, getColumn(fieldName));

        if (values == null || values.length == 0 || values[0] == null) {
            IsNullExpression isNullExpression = new IsNullExpression();
            isNullExpression.setLeftExpression(column);
            if (Objects.equals(operator, Operator.NOT_NULL)) {
                isNullExpression.setNot(true);
            }
            return isNullExpression;
        }

        BinaryExpression expr = getBinaryExpression(operator);
        if (expr != null) {
            expr.setLeftExpression(column);
            expr.setRightExpression(PARAM_EXPR);
            params.add(values[0]);
            return expr;
        }

        // 剩下 IN 和 NOT_IN
        List<Expression> expressions = new ArrayList<>(values.length);
        for (Object obj : values) {
            expressions.add(PARAM_EXPR);
            params.add(obj);
        }

        InExpression inExpression = new InExpression(column, new ExpressionList(expressions));
        if (Objects.equals(operator, Operator.NOT_IN)) {
            inExpression.setNot(true);
        }
        return inExpression;
    }

    protected BinaryExpression getBinaryExpression(Operator operator) {
        switch (operator) {
            case EQ:
                return new EqualsTo();
            case NOT_EQ:
                return new NotEqualsTo();
            case LT:
                return new MinorThan();
            case GT:
                return new GreaterThan();
            case LTEQ:
                return new MinorThanEquals();
            case GTEQ:
                return new GreaterThanEquals();
            case LIKE:
                return new LikeExpression();
            case NOT_LIKE:
                LikeExpression likeExpression = new LikeExpression();
                likeExpression.setNot(true);
                return likeExpression;
        }
        return null;
    }


}
