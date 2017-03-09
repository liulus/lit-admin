package com.lit.dao.builder;

import com.lit.commons.bean.BeanUtils;
import com.lit.dao.enums.FieldType;
import com.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2016-12-3 18:24
 * version $Id: DeleteBuilder.java, v 0.1 Exp $
 */
class DeleteBuilder extends AbstractSqlBuilder {

    private WhereBuilder whereBuilder;

    DeleteBuilder(Class<?> clazz) {
        super(clazz);
        whereBuilder = new WhereBuilder(tableInfo);
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
        if (entity != null) {
            Object obj = BeanUtils.invokeReaderMethod(entity, tableInfo.getPkField());
            if (obj != null && (!(obj instanceof String) || StringUtils.isNotBlank((String) obj))) {
                whereBuilder.add("and", tableInfo.getPkField(), "=", FieldType.WHERE, obj);
            } else {
                throw new NullPointerException("entity ["+entity+"] id is null, can not delete!");
            }
        }
    }

    @Override
    public void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values) {
        whereBuilder.add(logicOperator, fieldName, fieldOperator, fieldType, values);
    }

    @Override
    public SqlResult build() {
        StringBuilder sql = new StringBuilder("delete from ").append(tableInfo.getTableName());

        SqlResult whereResult = whereBuilder.build();
        sql.append(" ").append(whereResult.getSql());
        return new SqlResult(sql.toString(), whereResult.getParams());
    }

}
