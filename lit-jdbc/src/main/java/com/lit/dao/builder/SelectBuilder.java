package com.lit.dao.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2016-12-3 18:59
 * version $Id: SelectBuilder.java, v 0.1 Exp $
 */
class SelectBuilder extends AbstractSqlBuilder {

    private WhereBuilder whereBuilder;

    private OrderByBuilder orderByBuilder;

    private StringBuilder include;

    private StringBuilder func;

    private Map<String, String> defaultFields;

    SelectBuilder(Class<?> clazz) {
        super(clazz);
        whereBuilder = new WhereBuilder(tableInfo);
        orderByBuilder = new OrderByBuilder(tableInfo);
        include = new StringBuilder();
        func = new StringBuilder();
        // tableInfo的实例只有一份
        defaultFields = new HashMap<>(tableInfo.getFieldColumnMap());
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
    }

    @Override
    public void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values) {
        if (fieldType == FieldType.INCLUDE) {
            String columnName = tableInfo.getFieldColumnMap().get(StringUtils.trim(fieldName));
            Objects.requireNonNull(columnName, String.format("fieldName [ %s ] not exist!", fieldName));
            include.append(StringUtils.isEmpty(include) ? "" : ", ").append(columnName);
        } else if (fieldType == FieldType.EXCLUDE) {
            defaultFields.remove(fieldName);
        } else if (fieldType == FieldType.WHERE || fieldType == FieldType.BRACKET_BEGIN || fieldType == FieldType.BRACKET_END) {
            whereBuilder.add(logicOperator, fieldName, fieldOperator, fieldType, values);
        } else if (fieldType == FieldType.ORDER_BY_ASC || fieldType == FieldType.ORDER_BY_DESC) {
            orderByBuilder.add(logicOperator, fieldName, fieldOperator, fieldType, values);
        } else if (fieldType == FieldType.FUNC) {
            func.append(StringUtils.isEmpty(func) ? "" : ", ").append(parseFunc(fieldName));
        }
    }

    private String parseFunc(String funcStr) {
        if (StringUtils.contains(funcStr, '(') && StringUtils.contains(funcStr, ')')) {
            int start = StringUtils.indexOf(funcStr, '(') + 1;
            int end = StringUtils.indexOf(funcStr, ')');
            String fieldName = StringUtils.trim(StringUtils.substring(funcStr, start, end));
            String column = tableInfo.getFieldColumnMap().get(fieldName);
            return StringUtils.isEmpty(column) ? funcStr : StringUtils.substring(funcStr, 0, start) + column + ')';
        }
        return funcStr;
    }

    @Override
    public SqlResult build() {
        StringBuilder sql = new StringBuilder("select ");
        if (StringUtils.isNotEmpty(func)) {
            sql.append(func);
        } else if (StringUtils.isNotEmpty(include)) {
            sql.append(include);
        } else {
            sql.append(StringUtils.join(defaultFields.values(), ", "));
        }
        SqlResult whereResult = whereBuilder.build();
        sql.append(" from ").append(tableInfo.getTableName()).append(" ").append(whereResult.getSql());
        if (StringUtils.isEmpty(func)) {
            SqlResult orderByResult = orderByBuilder.build();
            sql.append(orderByResult.getSql());
        }
        return new SqlResult(sql.toString(), new ArrayList<>(whereResult.getParams()));
    }

}
