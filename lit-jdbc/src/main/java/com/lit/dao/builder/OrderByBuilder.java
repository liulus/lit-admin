package com.lit.dao.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * User : liulu
 * Date : 2017-1-1 20:33
 * version $Id: OrderByBuilder.java, v 0.1 Exp $
 */
class OrderByBuilder extends AbstractSqlBuilder {

    private StringBuilder orderBySql;

    OrderByBuilder(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        this.orderBySql = new StringBuilder();
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
    }

    @Override
    public void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values) {
        String columnName = tableInfo.getFieldColumnMap().get(StringUtils.trim(fieldName));
        Objects.requireNonNull(columnName, String.format("fieldName [ %s ] not exist!", fieldName));
        orderBySql.append(StringUtils.isEmpty(orderBySql) ? " " : ", ").append(columnName);
        if (fieldType == FieldType.ORDER_BY_ASC) {
            orderBySql.append(" asc");
        } else if (fieldType == FieldType.ORDER_BY_DESC) {
            orderBySql.append(" desc");
        }
    }

    @Override
    public SqlResult build() {
        if (StringUtils.isEmpty(orderBySql)) {
            return SqlResult.EMPTY_RESULT;
        }
        return new SqlResult("order by" + orderBySql, null);
    }

    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }
}
