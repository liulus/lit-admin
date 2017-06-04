package net.skeyurt.lit.jdbc.sta;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.jdbc.model.StatementContext;
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
     * 整体 createSelect 语句
     */
    private net.sf.jsqlparser.statement.select.Select select = new net.sf.jsqlparser.statement.select.Select();

    private PlainSelect plainSelect = new PlainSelect();

    /**
     * createSelect 语句的列，包括函数
     */
    private List<SelectItem> selectItems;

    /**
     * order by语句
     */
    private List<OrderByElement> orderBy;

    private List<String> exclude;

    private Class<T> entityClass;

    private static List<SelectItem> countFuncItem;


    SelectImpl(Class<T> clazz) {
        super(clazz);
        entityClass = clazz;
        initSelect();
    }

    private void initSelect() {
        selectItems = new ArrayList<>(tableInfo.getFieldColumnMap().size());

        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(new Table(tableInfo.getTableName()));
        plainSelect.setOrderByElements(orderBy);

        select.setSelectBody(plainSelect);
    }

    @Override
    public Select<T> include(String... fieldNames) {

        for (String fieldName : fieldNames) {
            selectItems.add(new SelectExpressionItem(new Column(getColumn(fieldName))));
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
    public Select<T> addField(String... fieldNames) {
        return null;
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
                funcColumns.add(new Column(getColumn(fieldName)));
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
        plainSelect.setSelectItems(getCountFuncItem());
        int count = (int) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_OBJECT, int.class));
        plainSelect.setOrderByElements(orderBy);
        plainSelect.setSelectItems(selectItems);
        return count;
    }

    @Override
    public T single() {
        processSelect();
        return (T) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_SINGLE, entityClass));
    }

    @Override
    public List<T> list() {
        processSelect();
        return (List<T>) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_LIST, entityClass));
    }

    @Override
    public PageList<T> pageList(Pager pager) {
        return pageList(pager.getPageSize(), pager.getPageNum(), pager.isCount());
    }

    @Override
    public PageList<T> pageList(int pageSize, int pageNum) {
        return pageList(pageSize, pageNum, true);
    }

    @Override
    public PageList<T> pageList(int pageSize, int pageNum, boolean queryCount) {
        processSelect();
        PageList<T> result;
        if (queryCount) {
            plainSelect.setOrderByElements(null);
            plainSelect.setSelectItems(getCountFuncItem());
            String countSql = pageHandler.getCountSql(dbName, plainSelect);
            int totalRecord = (int) executor.execute(new StatementContext(countSql, params, StatementContext.StatementType.SELECT_OBJECT, int.class));
            result = new PageList<>(pageSize, pageNum, totalRecord);
            plainSelect.setOrderByElements(orderBy);
            plainSelect.setSelectItems(selectItems);
            if (Objects.equals(totalRecord, 0)) {
                return result;
            }
        } else {
            result = new PageList<>(pageSize);
        }

        String pageSql = pageHandler.getPageSql(dbName, select.toString(), pageSize, pageNum);
        List execute = (List) executor.execute(new StatementContext(pageSql, params, StatementContext.StatementType.SELECT_LIST, entityClass));

        result.addAll(execute);

        return result;
    }

    @Override
    public <E> E objResult(Class<E> clazz) {
        processSelect();
        return (E) executor.execute(new StatementContext(select.toString(), params, StatementContext.StatementType.SELECT_SINGLE, clazz));
    }

    private void processSelect() {

        if (selectItems.size() == 0) {

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

    private List<SelectItem> getCountFuncItem() {
        if (countFuncItem == null) {
            countFuncItem = new ArrayList<>();
            Function countFunc = new Function();
            countFunc.setName("count");
            countFunc.setAllColumns(true);

            countFuncItem.add(new SelectExpressionItem(countFunc));
        }

        return countFuncItem;
    }
}
