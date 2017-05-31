package net.skeyurt.lit.dao.builder;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.spi.expr.LeftParenthesis;
import net.skeyurt.lit.dao.spi.expr.RightParenthesis;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/5/28 22:05
 * version $Id: SqlWhere.java, v 0.1 Exp $
 */
public abstract class SqlWhere extends AbstractSqlBuilder {

    protected Expression where;

    SqlWhere(Class<?> clazz) {
        super(clazz);
    }


    protected void addExpr(String fieldName, Operator operator, boolean isAnd, boolean useBracket, Object... values) {
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

    protected void addEnd() {
        if (where != null) {
            where = new RightParenthesis(where);
        }
    }


    private Expression getExpression(String fieldName, Operator operator, Object... values) {

        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }

        String columnName = getColumn(fieldName);
        Column column = new Column(columnName);

        if (values == null || values.length == 0 || values[0] == null) {
            IsNullExpression isNullExpression = new IsNullExpression();
            isNullExpression.setLeftExpression(column);
            if (Objects.equals(operator, Operator.NOT_NULL)) {
                isNullExpression.setNot(true);
            }
            return isNullExpression;
        }

        BinaryExpression expr = null;
        switch (operator) {
            case EQ:
                expr = new EqualsTo();
                break;
            case NOT_EQ:
                expr = new NotEqualsTo();
                break;
            case LT:
                expr = new MinorThan();
                break;
            case GT:
                expr = new GreaterThan();
                break;
            case LTEQ:
                expr = new MinorThanEquals();
                break;
            case GTEQ:
                expr = new GreaterThanEquals();
                break;
            case LIKE:
                expr = new LikeExpression();
                break;
            case NOT_LIKE:
                LikeExpression likeExpression = new LikeExpression();
                likeExpression.setNot(true);
                expr = likeExpression;
                break;
        }
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

}
