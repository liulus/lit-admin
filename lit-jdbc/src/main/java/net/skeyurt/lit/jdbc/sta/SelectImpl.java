package net.skeyurt.lit.jdbc.sta;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.jdbc.enums.JoinType;
import net.skeyurt.lit.jdbc.enums.Operator;
import net.skeyurt.lit.jdbc.model.StatementContext;
import net.skeyurt.lit.jdbc.model.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * User : liulu
 * Date : 2017/6/4 9:33
 * version $Id: SelectImpl.java, v 0.1 Exp $
 */
@SuppressWarnings("unchecked")
class SelectImpl<T> extends AbstractCondition<Select<T>> implements Select<T> {

    /**
     * 整体 Select 语句
     */
    private net.sf.jsqlparser.statement.select.Select select;

    private PlainSelect plainSelect;

    /**
     * Select 语句的列，包括函数
     */
    private List<SelectItem> selectItems;

    /**
     * from 的所有 table 对象
     */
    private Map<Class<?>, Table> from;

    /**
     * join 语句
     */
    private List<Join> joins;

    /**
     * join 表的 表信息
     */
    private Map<Class<?>, TableInfo> joinTableMap;

    /**
     * groupBy 语句
     */
    private List<Expression> groupBy;

    /**
     * having 字句
     */
    private Expression having;

    /**
     * order by语句
     */
    private List<OrderByElement> orderBy;

    /**
     * 需要排除的字段
     */
    private List<String> exclude;

    private int addFieldLength = 0;

    private Class<T> entityClass;

    private Integer pageNum;

    private Integer pageSize;

    private boolean queryCount = true;

    private static List<SelectItem> COUNT_FUNC_ITEM;

    static {
        Function countFunc = new Function();
        countFunc.setName("count");
        countFunc.setAllColumns(true);

        SelectItem item = new SelectExpressionItem(countFunc);
        COUNT_FUNC_ITEM = Collections.singletonList(item);
    }

    SelectImpl(Class<T> clazz) {
        super(clazz);
        entityClass = clazz;
        initSelect();
    }

    private void initSelect() {
        select = new net.sf.jsqlparser.statement.select.Select();
        selectItems = new ArrayList<>(tableInfo.getFieldColumnMap().size());
        from = new HashMap<>();
        from.put(entityClass, new Table(tableInfo.getTableName()));

        plainSelect = new PlainSelect();
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(from.get(entityClass));
        plainSelect.setJoins(joins);
        plainSelect.setGroupByColumnReferences(groupBy);
        plainSelect.setOrderByElements(orderBy);

        select.setSelectBody(plainSelect);
    }

    @Override
    public Select<T> include(String... fieldNames) {

        for (String fieldName : fieldNames) {
            selectItems.add(new SelectExpressionItem(new Column(from.get(entityClass), getColumn(fieldName))));
        }
        return this;
    }

    @Override
    public Select<T> exclude(String... fieldNames) {
        if (exclude == null) {
            exclude = new ArrayList<>(fieldNames.length);
        }
        for (String fieldName : fieldNames) {
            exclude.add(StringUtils.trim(fieldName));
        }
        return this;
    }

    @Override
    public Select<T> addField(Class<?> tableClass, String... fieldNames) {
        addFieldLength += fieldNames.length;
        if (joins == null) {
            joins = new ArrayList<>();
            joinTableMap = new HashMap<>();
        }
        if (joinTableMap.get(tableClass) == null) {
            TableInfo tableInfo = new TableInfo(tableClass);
            joinTableMap.put(tableClass, tableInfo);
            from.put(tableClass, new Table(tableInfo.getTableName()));
        }

        for (String fieldName : fieldNames) {
            selectItems.add(new SelectExpressionItem(getColumn(tableClass, fieldName)));
        }
        return this;
    }

    @Override
    public Select<T> addFunc(String funcName) {
        addFunc(funcName, false, true);
        return this;
    }

    @Override
    public Select<T> addFunc(String funcName, String... fieldNames) {
        addFunc(funcName, false, false, fieldNames);
        return this;
    }

    @Override
    public Select<T> addFunc(String funcName, boolean distinct, String... fieldNames) {
        addFunc(funcName, distinct, false, fieldNames);
        return this;
    }

    @Override
    public Select<T> addFunc(String funcName, boolean distinct, boolean allColumns, String... fieldNames) {
        Function function = new Function();

        boolean noFuncColumns = fieldNames == null || fieldNames.length == 0 || fieldNames[0] == null;
        if (!noFuncColumns) {
            List<Expression> funcColumns = new ArrayList<>(fieldNames.length);
            for (String fieldName : fieldNames) {
                funcColumns.add(new Column(from.get(entityClass), getColumn(fieldName)));
            }
            function.setParameters(new ExpressionList(funcColumns));
        }

        function.setName(funcName);
        function.setAllColumns(allColumns);
        function.setDistinct(distinct);

        selectItems.add(new SelectExpressionItem(function));
        return this;
    }

    @Override
    public Select<T> alias(String... alias) {
        int size = selectItems.size();
        for (int i = size - alias.length; i < size; i++) {
            ((SelectExpressionItem) selectItems.get(i)).setAlias(new Alias(alias[i]));
        }
        return this;
    }

    @Override
    public Select<T> tableAlias(String alias) {
        if (joins == null || joins.size() == 0) {
            from.get(entityClass).setAlias(new Alias(alias));
        } else {
            Join join = getLastJoin();
            join.getRightItem().setAlias(new Alias(alias));
        }
        return this;
    }

    @Override
    public <E> Select<T> join(Class<E> tableClass) {
        return join(tableClass, false);
    }

    @Override
    public <E> Select<T> simpleJoin(Class<E> tableClass) {
        return join(tableClass, true);
    }

    private <E> Select<T> join(Class<E> tableClass, boolean simple) {
        if (joins == null) {
            joins = new ArrayList<>();
            joinTableMap = new HashMap<>();
        }
        if (joinTableMap.get(tableClass) == null) {
            TableInfo tableInfo = new TableInfo(tableClass);
            joinTableMap.put(tableClass, tableInfo);
            from.put(tableClass, new Table(tableInfo.getTableName()));
        }

        Join join = new Join();
        join.setRightItem(from.get(tableClass));
        join.setSimple(simple);
        joins.add(join);

        return this;
    }

    @Override
    public <E> Select<T> join(JoinType joinType, Class<E> tableClass) {
        join(tableClass);

        Join join = getLastJoin();
        switch (joinType) {
            case RIGHT:
                join.setRight(true);
                break;
            case LEFT:
                join.setLeft(true);
                break;
            case FULL:
                join.setFull(true);
                break;
            case NATURAL:
                join.setNatural(true);
            case CROSS:
                join.setCross(true);
            case INNER:
                join.setInner(true);
            case OUTER:
                join.setOuter(true);
            case SEMI:
                join.setSemi(true);
        }

        return this;
    }

    @Override
    public Select<T> on(Class<?> table1, String field1, Operator operator, Class<?> table2, String field2) {
        Join join = getLastJoin();

        BinaryExpression expression = getBinaryExpression(operator);
        expression.setLeftExpression(getColumn(table1, field1));
        expression.setRightExpression(getColumn(table2, field2));

        join.setOnExpression(expression);
        return this;
    }

    private Column getColumn(Class<?> clazz, String field) {
        if (Objects.equals(clazz, entityClass)) {
            String column = getColumn(field);
            return new Column(from.get(clazz), column);
        }
        String column = joinTableMap.get(clazz).getFieldColumnMap().get(StringUtils.trim(field));
        return StringUtils.isEmpty(column) ? new Column(from.get(clazz), field) : new Column(from.get(clazz), column);
    }

    private Join getLastJoin() {
        return joins.get(joins.size() - 1);
    }

    @Override
    public Select<T> joinCondition(Class<?> table1, String field1, Operator operator, Class<?> table2, String field2) {

        BinaryExpression expression = getBinaryExpression(operator);
        expression.setLeftExpression(getColumn(table1, field1));
        expression.setRightExpression(getColumn(table2, field2));
        where = where == null ? expression : new AndExpression(where, expression);
        return this;
    }

    @Override
    public Select<T> groupBy(String... fields) {
        if (groupBy == null) {
            groupBy = new ArrayList<>(fields.length);
        }
        for (String field : fields) {
            groupBy.add(new Column(from.get(entityClass), getColumn(field)));
        }

        return this;
    }

    @Override
    public Select<T> asc(String... fieldNames) {
        return order(true, fieldNames);
    }

    @Override
    public Select<T> desc(String... fieldNames) {
        return order(false, fieldNames);
    }

    private Select<T> order(boolean asc, String... fieldNames) {
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
    public int count() {
        processSelect();
        plainSelect.setOrderByElements(null);
        plainSelect.setSelectItems(COUNT_FUNC_ITEM);
        int count = (int) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_SINGLE, int.class));
        plainSelect.setOrderByElements(orderBy);
        plainSelect.setSelectItems(selectItems);
        return count;
    }

    @Override
    public T single() {
        return single(entityClass);
    }

    @Override
    public <E> E single(Class<E> clazz) {
        processSelect();
        return (E) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_SINGLE, clazz));
    }

    @Override
    public List<T> list() {
        return list(entityClass);
    }

    @Override
    public <E> List<E> list(Class<E> clazz) {

        if (pageNum != null && pageSize != null) {
            PageList<E> result;
            if (queryCount) {
                int totalRecord = count();
                result = new PageList<>(pageSize, pageNum, totalRecord);
                if (Objects.equals(totalRecord, 0)) {
                    return result;
                }
            } else {
                processSelect();
                result = new PageList<>(pageSize);
            }

            String pageSql = pageHandler.getPageSql(dbName, select.toString(), pageSize, pageNum);
            List execute = (List) executor.execute(new StatementContext(pageSql, params, StatementContext.StatementType.SELECT_LIST, clazz));

            result.addAll(execute);

            return result;
        }

        processSelect();
        return (List<E>) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_LIST, clazz));
    }

    @Override
    public Select<T> pageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    @Override
    public Select<T> pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Select<T> count(boolean queryCount) {
        this.queryCount = queryCount;
        return this;
    }

    private void processSelect() {

        if (selectItems.size() == addFieldLength) {

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
            plainSelect.setWhere(where);
        }
    }

}
