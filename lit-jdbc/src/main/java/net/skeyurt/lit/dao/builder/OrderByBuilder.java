package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;
import net.skeyurt.lit.dao.model.TableInfo;
import org.apache.commons.lang3.StringUtils;

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
    public void add(String logicOperator, String fieldName, Operator fieldOperator, FieldType fieldType, Object... values) {
        String columnName = getColumn(fieldName);
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
}
