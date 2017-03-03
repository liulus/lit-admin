package com.lit.commons.page;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User : liulu
 * Date : 2017-2-17 22:39
 * version $Id: DefaultPageSqlHandler.java, v 0.1 Exp $
 */
public class DefaultPageSqlHandler implements PageSqlHandler {

    // MySQL 分页，参数1 ：第几条开始( offset ); 参数2：查询多少条(pageSize)
    private static final String MYSQL_PAGE_SQL = "%s limit %d, %d ";

    // DB2 分页，参数1 ：第几条开始( offset ); 参数2：第几条为止(maxResult)
    private static final String DB2_PAGE_SQL = "select * from ( select t.*, rownumber() over() rowid from ( %s ) t ) where rowid > %d ) and rowid <= %d ";

    // Oracle 分页，参数1：第几条为止(maxResult); 参数2 ：第几条开始( offset )
    private static final String ORACLE_PAGE_SQL = "select * from (select t.*, rownum rowno from ( %s ) t where rownum <= %d ) where rowno > %d ";

    private static final List<SelectItem> COUNT_ITEM = new ArrayList<>();

    private static final Alias TABLE_ALIAS = new Alias("table_count");

    public DefaultPageSqlHandler() {
        COUNT_ITEM.add(new SelectExpressionItem(new Column("count(*)")));
        TABLE_ALIAS.setUseAs(false);
    }

    @Override
    public String getPageSql(String sql, String dbName, int pageSize, int pageNum) {
        switch (dbName) {
            case "DB2":
                return String.format(DB2_PAGE_SQL, sql, pageSize * (pageNum - 1), pageSize * pageNum);
            case "MYSQL":
                return String.format(MYSQL_PAGE_SQL, sql, pageSize * (pageNum - 1), pageSize);
            case "ORACLE":
                return String.format(ORACLE_PAGE_SQL, sql, pageSize * pageNum, pageSize * (pageNum - 1));
            default:
                throw new UnsupportedOperationException("不支持此数据库的分页, unsupport database: " + dbName);
        }
    }

    private final Map<String, String> SQL_CACHE = new ConcurrentHashMap<>();

    @Override
    public String getCountSql(String sql, String dbName) {
        checkSupport(sql);
        if (StringUtils.isNotEmpty(SQL_CACHE.get(sql))) {
            return SQL_CACHE.get(sql);
        }

        //解析SQL
        Statement stmt;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (Throwable e) {
            //无法解析的用一般方法返回count语句
            String countSql = "select count(*) from (" + sql + ") tmp_count";
            SQL_CACHE.put(sql, countSql);
            return countSql;
        }

        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();

        //处理body-去order by
        processSelectBody(selectBody);

        //处理with-去order by
        processWithItemsList(select.getWithItemsList());

        //处理为count查询
        sqlToCount(select);

        String result = select.toString();
        SQL_CACHE.put(sql, result);

        return result;
    }

    protected void checkSupport(String sql) {
        if (StringUtils.contains(StringUtils.lowerCase(sql), "for update")) {
            throw new UnsupportedOperationException("不支持包含for update的sql");
        }
    }

    /**
     * 是否可以用简单的count查询方式
     *
     * @param select
     * @return
     */
    protected boolean isSimpleCount(PlainSelect select) {

        //包含group by的时候不可以
        if (select.getGroupByColumnReferences() != null) {
            return false;
        }
        //包含distinct的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        for (SelectItem item : select.getSelectItems()) {
            //select列中包含参数的时候不可以，否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            //如果查询列中包含函数，也不可以，函数可能会聚合列
            if (item instanceof SelectExpressionItem) {
                if (((SelectExpressionItem) item).getExpression() instanceof Function) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 将sql转换为count查询
     *
     * @param select
     */
    protected void sqlToCount(Select select) {
        SelectBody selectBody = select.getSelectBody();
        // 是否能简化count查询
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(COUNT_ITEM);
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(COUNT_ITEM);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 处理WithItem
     *
     * @param withItemsList
     */
    protected void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            for (WithItem item : withItemsList) {
                processSelectBody(item.getSelectBody());
            }
        }
    }

    /**
     * 处理selectBody去除Order by
     *
     * @param selectBody
     */
    protected void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                List<SelectBody> plainSelects = operationList.getSelects();
                for (SelectBody plainSelect : plainSelects) {
                    processSelectBody(plainSelect);
                }
            }
            if (!orderByHashParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    /**
     * 处理PlainSelect类型的selectBody
     *
     * @param plainSelect
     */
    protected void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 处理子查询
     *
     * @param fromItem
     */
    protected void processFromItem(FromItem fromItem) {

        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoin() != null) {
                if (subJoin.getJoin().getRightItem() != null) {
                    processFromItem(subJoin.getJoin().getRightItem());
                }
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * 判断Order by是否包含参数，有参数的不能去
     *
     * @param orderByElements
     * @return
     */
    protected boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }
        return false;
    }
}
