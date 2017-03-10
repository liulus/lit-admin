package com.lit.dao.builder;

import com.lit.dao.enums.FieldType;
import com.lit.dao.model.SqlResult;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User : liulu
 * Date : 2016-11-23 20:14
 * version $Id: InsertBuilder.java, v 0.1 Exp $
 */
class InsertBuilder extends AbstractSqlBuilder {

    InsertBuilder(Class<?> clazz) {
        super(clazz);
        fieldValueMap = new HashMap<>();
    }

    @Override
    public void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values) {
        if (values == null || values.length == 0) {
            fieldValueMap.put(fieldName, null);
        } else if (values.length > 1) {
            throw new IllegalArgumentException("insert only one args!");
        } else {
            fieldValueMap.put(fieldName, values[0]);
        }
    }

    @Override
    public SqlResult build() {

        if (fieldValueMap == null || fieldValueMap.size() == 0) {
            return SqlResult.EMPTY_RESULT;
        }
        StringBuilder sql = new StringBuilder("insert into ").append(tableInfo.getTableName()).append(" ( ");
        StringBuilder values = new StringBuilder(" values ( ");
        for (String fieldName : fieldValueMap.keySet()) {
            sql.append(tableInfo.getFieldColumnMap().get(fieldName)).append(", ");
            values.append("?, ");
        }
        sql.deleteCharAt(sql.lastIndexOf(",")).append(")").append(values).deleteCharAt(sql.lastIndexOf(",")).append(")");

        return new SqlResult(sql.toString(), new ArrayList<>(fieldValueMap.values()));
    }

}
