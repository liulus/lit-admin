package com.lit.dao.builder;

import com.lit.dao.enums.FieldType;
import com.lit.dao.model.SqlResult;
import com.lit.dao.model.TableInfo;

/**
 * User : liulu
 * Date : 2016-11-23 20:26
 * version $Id: SqlBuilder.java, v 0.1 Exp $
 */
public interface SqlBuilder {

    /**
     * insert 和 update 的时候初始化实体的属性名和属性值的信息
     *
     * @param entity       实体
     * @param isIgnoreNull 是否忽略 null 值
     */
    void initEntity(Object entity, Boolean isIgnoreNull);

    /**
     * 将对应的操作添加到对应的 builder 上
     *
     * @param logicOperator 逻辑运算符 -> where 条件中的 and 和 or
     * @param fieldName     字段名 -> 实体中的属性名
     * @param fieldOperator 字段操作符 -> =, !=, >, <, >=, =<, like, in, not in;
     * @param fieldType     字段操作类型， 用于区分哪个 builder 进行操作
     * @param values        参数值
     */
    void add(String logicOperator, String fieldName, String fieldOperator, FieldType fieldType, Object... values);

    /**
     * 构建 sql 结果，包括 sql 语句和参数值
     *
     * @return SqlResult
     */
    SqlResult build();

    /**
     * 获取操作对象的表信息
     * @return
     */
    TableInfo getTableInfo();
}
