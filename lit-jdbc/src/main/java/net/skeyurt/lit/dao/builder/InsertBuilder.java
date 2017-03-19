package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;

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
        columnValueMap = new HashMap<>();
    }

    @Override
    public void add(String logicOperator, String fieldName, Operator fieldOperator, FieldType fieldType, Object... values) {
        String column = getColumn(fieldName);
        if (values == null || values.length == 0 || values[0] == null) {
            initNativeMap();
            nativeValueMap.put(column, null);
        } else if (values.length > 1) {
            throw new IllegalArgumentException("insert 参数个数错误!");
        } else if (fieldType == FieldType.INSERT_NATIVE) {
            initNativeMap();
            nativeValueMap.put(column, values[0]);
        } else {
            columnValueMap.put(column, values[0]);
        }
    }

    @Override
    public SqlResult build() {

        if (columnValueMap == null || columnValueMap.size() == 0 || nativeValueMap == null) {
            return SqlResult.EMPTY_RESULT;
        }
        StringBuilder sql = new StringBuilder("insert into ").append(tableInfo.getTableName()).append(" ( ");
        StringBuilder values = new StringBuilder(" values ( ");
        if (nativeValueMap != null && nativeValueMap.size() > 0) {
            for (String column : nativeValueMap.keySet()) {
                sql.append(column).append(", ");
                values.append(nativeValueMap.get(column)).append(", ");
            }
        }

        for (String column : columnValueMap.keySet()) {
            sql.append(column).append(", ");
            values.append("?, ");
        }

        sql.deleteCharAt(sql.lastIndexOf(",")).append(")").append(values).deleteCharAt(sql.lastIndexOf(",")).append(")");
        return new SqlResult(sql.toString(), new ArrayList<>(columnValueMap.values()));
    }

    private void initNativeMap() {
        if (nativeValueMap == null) {
            nativeValueMap = new HashMap<>();
        }
    }

}
