package net.skeyurt.lit.dao.builder;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/5/28 22:03
 * version $Id: SqlSelect.java, v 0.1 Exp $
 */
public class SqlSelect<T> extends SqlWhere {

    /**
     * 整体 select 语句
     */
    private PlainSelect select = new PlainSelect();

    /**
     * select 语句的列，包括函数
     */
    private List<SelectItem> selectItems;

    /**
     * select 语句的函数
     */
    private List<Function> functions;

    /**
     * order by语句
     */
    private List<OrderByElement> orderBy;

    /**
     * 用户指定的 查询列
     */
    private List<String> include;

    private List<String> exclude;

    private Class<T> entityClass;

    SqlSelect(Class<T> clazz) {
        super(clazz);
        entityClass = clazz;
        initSelect();
    }

    private void initSelect() {
        selectItems = new ArrayList<>(tableInfo.getFieldColumnMap().size());

        select.setSelectItems(selectItems);
        select.setFromItem(new Table(tableInfo.getTableName()));
        select.setOrderByElements(orderBy);
    }

    /**
     * 查询语句中 要查询的属性(不能和 exclude 同时使用)
     *
     * @param fieldNames
     * @return
     */
    public SqlSelect<T> include(String... fieldNames) {
        if (include == null) {
            include = new ArrayList<>(fieldNames.length);
        }

        for (String fieldName : fieldNames) {
            include.add(getColumn(fieldName));
        }
        return this;
    }

    /**
     * 查询语句中 要排除的属性
     *
     * @param fieldNames
     * @return
     */
    public SqlSelect<T> exclude(String... fieldNames) {
        if (exclude == null) {
            exclude = new ArrayList<>(fieldNames.length);
        }
        for (String fieldName : fieldNames) {
            exclude.add(StringUtils.trim(fieldName));
            if (include != null) {
                include.remove(getColumn(fieldName));
            }

        }
        return this;
    }

    /**
     * select 语句中添加函数，目前只支持合计函数（Aggregate functions）暂时不支持 group by 语句
     *
     * @param funcName 函数名 如 count, max,
     * @return
     */
    public SqlSelect<T> addFunc(String funcName) {
        addFunc(funcName, false, true);
        return this;
    }

    public SqlSelect<T> addFunc(String funcName, String... fieldNames) {
        addFunc(funcName, false, false, fieldNames);
        return this;
    }

    public SqlSelect<T> addFunc(String funcName, boolean distinct, String... fieldNames) {
        addFunc(funcName, distinct, false, fieldNames);
        return this;
    }


    public SqlSelect<T> addFunc(String funcName, boolean distinct, boolean allColumns, String... fieldNames) {
        Function function = new Function();

        boolean noFuncColumns = fieldNames == null || fieldNames.length == 0 || fieldNames[0] == null;
        if (!noFuncColumns) {
            List<Expression> funcColumns = new ArrayList<>(fieldNames.length);
            for (String fieldName : fieldNames) {
                funcColumns.add(new Column(getColumn(fieldName)));
            }
            function.setParameters(new ExpressionList(funcColumns));
        }

        function.setName(funcName);
        function.setAllColumns(allColumns);
        function.setDistinct(distinct);

        if (functions == null) {
            functions = new ArrayList<>();
        }
        functions.add(function);
        return this;
    }

    /**
     * 添加 where 条件，默认操作符 =
     *
     * @param fieldName 属性名
     * @param value     值
     * @return
     */
    public SqlSelect<T> where(String fieldName, Object value) {
        addExpr(fieldName, Operator.EQ, true, false, value);
        return this;
    }

    /**
     * 添加 where 条件
     *
     * @param fieldName
     * @param operator
     * @param values
     * @return
     */
    public SqlSelect<T> where(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, false, values);
        return this;
    }

    /**
     * 添加 and 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlSelect<T> and(String fieldName, Object value) {
        this.and(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlSelect<T> and(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, false, values);
        return this;
    }

    /**
     * 添加带 括号 的 and 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlSelect<T> andWithBracket(String fieldName, Object value) {
        this.andWithBracket(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 括号 的 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlSelect<T> andWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, true, values);
        return this;
    }

    /**
     * 添加 or 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlSelect<T> or(String fieldName, Object value) {
        this.or(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlSelect<T> or(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, false, values);
        return this;
    }

    /**
     * 添加带 括号 的 or 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlSelect<T> orWithBracket(String fieldName, Object value) {
        this.orWithBracket(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 括号 的 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlSelect<T> orWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, true, values);
        return this;
    }

    /**
     * 添加 where 条件中的结束 括号
     *
     * @return
     */
    public SqlSelect<T> end() {
        addEnd();
        return this;
    }

    /**
     * 添加升序排列属性
     *
     * @param fieldNames
     * @return
     */
    public SqlSelect<T> asc(String... fieldNames) {
        return order(true, fieldNames);
    }

    /**
     * 添加降序排列属性
     *
     * @param fieldNames
     * @return
     */
    public SqlSelect<T> desc(String... fieldNames) {
        return order(false, fieldNames);
    }

    private SqlSelect<T> order(boolean asc, String... fieldNames) {
        if (orderBy == null) {
            orderBy = new ArrayList<>();
        }

        for (String fieldName : fieldNames) {
            OrderByElement element = new OrderByElement();
            element.setExpression(new Column(getColumn(fieldName)));
            element.setAsc(asc);
            orderBy.add(element);
        }
        return this;
    }

    @Override
    public SqlResult build() {

        if (include != null) {
            for (String column : include) {
                selectItems.add(new SelectExpressionItem(new Column(column)));
            }
        }
        if (functions != null) {
            for (Function function : functions) {
                selectItems.add(new SelectExpressionItem(function));
            }
        }

        if (include == null && functions == null) {

            Map<String, String> fieldColumnMap = tableInfo.getFieldColumnMap();
            if (exclude != null) {
                for (String field : exclude) {
                    fieldColumnMap.remove(field);
                }
            }
            for (String column : fieldColumnMap.values()) {
                selectItems.add(new SelectExpressionItem(new Column(column)));
            }
        }

        if (where != null) {
            select.setWhere(where);
        }

        return new SqlResult(select.toString(), params);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
