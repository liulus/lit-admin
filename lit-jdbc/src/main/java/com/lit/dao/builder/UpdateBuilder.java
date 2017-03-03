package com.lit.dao.builder;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * User : liulu
 * Date : 2016-12-3 18:20
 * version $Id: UpdateBuilder.java, v 0.1 Exp $
 */
class UpdateBuilder extends AbstractSqlBuilder {

    private WhereBuilder whereBuilder;

    UpdateBuilder(Class<?> clazz) {
        super(clazz);
        fieldValueMap = new HashMap<>();
        whereBuilder = new WhereBuilder(tableInfo);
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
        super.initEntity(entity, isIgnoreNull);
        String pkFieldName = tableInfo.getPkField();
        if (fieldValueMap.get(pkFieldName) != null) {
            whereBuilder.add("and", pkFieldName, "=", FieldType.WHERE, fieldValueMap.get(pkFieldName));
            fieldValueMap.remove(pkFieldName);
        } else {
            throw new NullPointerException("entity ["+entity+"] id is null, can not update!");
        }
    }

    @Override
    public void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values) {
        if (fieldType == FieldType.UPDATE) {
            if (values == null || values.length == 0 || values[0] == null) {
                fieldValueMap.put(fieldName, null);
            } else if (values.length > 1) {
                throw new RuntimeException("update only one args!");
            } else {
                fieldValueMap.put(fieldName, values[0]);
            }
        } else if (fieldType == FieldType.WHERE || fieldType == FieldType.BRACKET_BEGIN || fieldType == FieldType.BRACKET_END) {
            whereBuilder.add(logicOperator, fieldName, fieldOperator, fieldType, values);
        }
    }

    @Override
    public SqlResult build() {
        if (CollectionUtils.isEmpty(fieldValueMap)) {
            return SqlResult.EMPTY_RESULT;
        }

        StringBuilder sql = new StringBuilder("update ").append(tableInfo.getTableName()).append(" set ");

        Iterator<Map.Entry<String, Object>> iterator = fieldValueMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> fieldValueEntry = iterator.next();
            sql.append(tableInfo.getFieldColumnMap().get(fieldValueEntry.getKey()));
            if (fieldValueEntry.getValue() == null) {
                sql.append(" = null, ");
                // 在参数列表中要移除 null 值
                iterator.remove();
            } else {
                sql.append(" = ?, ");
            }
        }
        SqlResult whereResult = whereBuilder.build();
        sql.deleteCharAt(sql.lastIndexOf(",")).append(whereResult.getSql());
        List<Object> args = new ArrayList<>(fieldValueMap.values().size() + whereResult.getParams().size());
        args.addAll(fieldValueMap.values());
        args.addAll(whereResult.getParams());
        return new SqlResult(sql.toString(), args);
    }

}
