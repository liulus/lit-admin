package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.GenerationType;
import net.skeyurt.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
            fieldValueMap.put(StringUtils.trim(fieldName), null);
        } else if (values.length > 1) {
            throw new IllegalArgumentException("insert only one args!");
        } else {
            fieldValueMap.put(StringUtils.trim(fieldName), values[0]);
        }
    }

    @Override
    public SqlResult build() {

        if (fieldValueMap == null || fieldValueMap.size() == 0) {
            return SqlResult.EMPTY_RESULT;
        }
        StringBuilder sql = new StringBuilder("insert into ").append(tableInfo.getTableName()).append(" ( ");
        StringBuilder values = new StringBuilder(" values ( ");
        boolean removeKey = false;
        for (String fieldName : fieldValueMap.keySet()) {
            sql.append(tableInfo.getFieldColumnMap().get(fieldName)).append(", ");
            if (tableInfo.isAutoGenerateKey() && StringUtils.equals(fieldName, tableInfo.getPkField())
                    && Objects.equals(GenerationType.SEQUENCE, tableInfo.getGenerationType())) {
                values.append(fieldValueMap.get(fieldName)).append(", ");
                removeKey = true;
            } else {
                values.append("?, ");
            }
        }
        if (removeKey) {
            fieldValueMap.remove(tableInfo.getPkField());
        }
        sql.deleteCharAt(sql.lastIndexOf(",")).append(")").append(values).deleteCharAt(sql.lastIndexOf(",")).append(")");

        return new SqlResult(sql.toString(), new ArrayList<>(fieldValueMap.values()));
    }

}
