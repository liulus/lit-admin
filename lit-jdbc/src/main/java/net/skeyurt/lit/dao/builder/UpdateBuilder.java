package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;

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
        columnValueMap = new HashMap<>();
        whereBuilder = new WhereBuilder(tableInfo);
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
        super.initEntity(entity, isIgnoreNull);
        String pkColumn = tableInfo.getPkColumn();
        if (columnValueMap.get(pkColumn) != null) {
            whereBuilder.add("and ", pkColumn, Operator.EQ, FieldType.WHERE, columnValueMap.get(pkColumn));
            columnValueMap.remove(pkColumn);
        } else {
            throw new NullPointerException("entity [" + entity + "] id is null, can not update!");
        }
    }

    @Override
    public void add(String logicOperator, String fieldName, Operator fieldOperator, FieldType fieldType, Object... values) {
        if (fieldType == FieldType.UPDATE) {
            String column = getColumn(fieldName);
            if (values == null || values.length == 0 || values[0] == null) {
                columnValueMap.put(column, null);
            } else if (values.length > 1) {
                throw new IllegalArgumentException("update 参数个数错误!");
            } else {
                columnValueMap.put(column, values[0]);
            }
        } else if (fieldType == FieldType.WHERE || fieldType == FieldType.BRACKET_BEGIN || fieldType == FieldType.BRACKET_END) {
            whereBuilder.add(logicOperator, fieldName, fieldOperator, fieldType, values);
        }
    }

    @Override
    public SqlResult build() {
        if (columnValueMap == null || columnValueMap.size() == 0) {
            return SqlResult.EMPTY_RESULT;
        }

        StringBuilder sql = new StringBuilder("update ").append(tableInfo.getTableName()).append(" set ");

        Iterator<Map.Entry<String, Object>> iterator = columnValueMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> columnValueEntry = iterator.next();
            sql.append(columnValueEntry.getKey());
            if (columnValueEntry.getValue() == null) {
                sql.append(" = null, ");
                // 在参数列表中要移除 null 值
                iterator.remove();
            } else {
                sql.append(" = ?, ");
            }
        }
        SqlResult whereResult = whereBuilder.build();
        sql.deleteCharAt(sql.lastIndexOf(",")).append(whereResult.getSql());

        List<Object> args = new ArrayList<>(columnValueMap.values().size() + whereResult.getParams().size());
        args.addAll(columnValueMap.values());
        args.addAll(whereResult.getParams());
        return new SqlResult(sql.toString(), args);
    }

}
