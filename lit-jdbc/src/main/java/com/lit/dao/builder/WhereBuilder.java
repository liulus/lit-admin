package com.lit.dao.builder;

import com.lit.dao.enums.FieldType;
import com.lit.dao.model.SqlResult;
import com.lit.dao.model.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2016-12-4 20:33
 * version $Id: WhereBuilder.java, v 0.1 Exp $
 */
class WhereBuilder extends AbstractSqlBuilder {

    private StringBuilder whereSql;

    private List<Object> params;


    WhereBuilder(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        whereSql = new StringBuilder();
        params = new ArrayList<>();
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
    }

    @Override
    public void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values) {
        String blank = " ";
        String columnName = tableInfo.getFieldColumnMap().get(StringUtils.trim(fieldName));
        fieldOperator = StringUtils.lowerCase(StringUtils.trim(fieldOperator));
        if (fieldType == FieldType.BRACKET_END) {
            whereSql.append(") ");
        } else if (values == null || values.length == 0 || values[0] == null) {
            Objects.requireNonNull(columnName, String.format("fieldName [ %s ] not exist!", fieldName));
            whereSql.append(logicOperator).append(columnName).append(StringUtils.contains("is=", fieldOperator) ? " is null " : " is not null ");
        } else if (StringUtils.contains(fieldOperator, "in")) {
            Objects.requireNonNull(columnName, String.format("fieldName [ %s ] not exist!", fieldName));
            whereSql.append(logicOperator).append(columnName).append(blank).append(fieldOperator).append(" ( ");
            for (Object obj : values) {
                whereSql.append("?, ");
                this.params.add(obj);
            }
            whereSql.deleteCharAt(whereSql.lastIndexOf(",")).append(") ");
        } else {
            Objects.requireNonNull(columnName, String.format("fieldName [ %s ] not exist!", fieldName));
            whereSql.append(logicOperator).append(columnName).append(blank).append(fieldOperator).append(" ? ");
            this.params.add(values[0]);
        }
    }

    @Override
    public SqlResult build() {
        if (StringUtils.isEmpty(whereSql)) {
            return SqlResult.EMPTY_RESULT;
        }
        String whereStr = StringUtils.startsWith(whereSql, "and") ? StringUtils.stripStart(whereSql.toString(), "and") : whereSql.toString();
        return new SqlResult("where " + whereStr, this.params);
    }


}
