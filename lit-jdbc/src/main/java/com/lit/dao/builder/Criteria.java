package com.lit.dao.builder;

/**
 * User : liulu
 * Date : 2016-12-3 17:39
 * version $Id: Criteria.java, v 0.1 Exp $
 */
public class Criteria {

    private Class<?> entityClass;

    private SqlBuilder sqlBuilder;

    private Criteria(Class<?> clazz, SqlBuilder sqlBuilder) {
        this.entityClass = clazz;
        this.sqlBuilder = sqlBuilder;
    }

    /**
     * 初始化 insert 语句
     *
     * @param clazz
     * @return
     */
    public static Criteria insert(Class<?> clazz) {
        return new Criteria(clazz, new InsertBuilder(clazz));
    }

    /**
     * 初始化 update 语句
     *
     * @param clazz
     * @return
     */
    public static Criteria update(Class<?> clazz) {
        return new Criteria(clazz, new UpdateBuilder(clazz));
    }

    /**
     * 初始化 delete 语句
     *
     * @param clazz
     * @return
     */
    public static Criteria delete(Class<?> clazz) {
        return new Criteria(clazz, new DeleteBuilder(clazz));
    }

    /**
     * 初始化 select 语句
     *
     * @param clazz
     * @return
     */
    public static Criteria select(Class<?> clazz) {
        return new Criteria(clazz, new SelectBuilder(clazz));
    }

    /**
     * insert into 的属性
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria into(String fieldName, Object value) {
        this.sqlBuilder.add(null, fieldName, null, FieldType.INSERT, value);
        return this;
    }

    /**
     * update set 的属性
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria set(String fieldName, Object value) {
        this.sqlBuilder.add(null, fieldName, null, FieldType.UPDATE, value);
        return this;
    }

    /**
     * 查询语句中 要查询的属性(不能和 exclude 同时使用)
     *
     * @param fieldNames
     * @return
     */
    public Criteria include(String... fieldNames) {
        for (String fieldName : fieldNames) {
            this.sqlBuilder.add(null, fieldName, null, FieldType.INCLUDE);
        }
        return this;
    }

    /**
     * 查询语句中 要排除的属性
     *
     * @param fieldNames
     * @return
     */
    public Criteria exclude(String... fieldNames) {
        for (String fieldName : fieldNames) {
            this.sqlBuilder.add(null, fieldName, null, FieldType.EXCLUDE);
        }
        return this;
    }

    /**
     * select 语句中添加函数，目前只支持合计函数（Aggregate functions）暂时不支持 group by 语句
     *
     * @param func 函数 如 count(*), max(age), 括号里的可以是属性名也可以是数据库字段名
     * @return
     */
    public Criteria addFunc(String func) {
        this.sqlBuilder.add(null, func, null, FieldType.FUNC);
        return this;
    }


    /**
     * 添加 where 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria where(String fieldName, Object value) {
        this.where(fieldName, "=", value);
        return this;
    }

    /**
     * 添加 where 条件
     *
     * @param fieldName
     * @param fieldOperator
     * @param values
     * @return
     */
    public Criteria where(String fieldName, String fieldOperator, Object... values) {
        this.sqlBuilder.add("", fieldName, fieldOperator, FieldType.WHERE, values);
        return this;
    }

    /**
     * 添加 and 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria and(String fieldName, Object value) {
        this.and(fieldName, "=", value);
        return this;
    }

    /**
     * 添加 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria and(String fieldName, String fieldOperator, Object... values) {
        this.sqlBuilder.add("and ", fieldName, fieldOperator, FieldType.WHERE, values);
        return this;
    }

    /**
     * 添加带 括号 的 and 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria andWithBracket(String fieldName, Object value) {
        this.andWithBracket(fieldName, "=", value);
        return this;
    }

    /**
     * 添加 括号 的 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria andWithBracket(String fieldName, String fieldOperator, Object... values) {
        this.sqlBuilder.add("and ( ", fieldName, fieldOperator, FieldType.WHERE, values);
        return this;
    }

    /**
     * 添加 or 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria or(String fieldName, Object value) {
        this.or(fieldName, "=", value);
        return this;
    }

    /**
     * 添加 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria or(String fieldName, String fieldOperator, Object... values) {
        this.sqlBuilder.add("or ", fieldName, fieldOperator, FieldType.WHERE, values);
        return this;
    }

    /**
     * 添加带 括号 的 or 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria orWithBracket(String fieldName, Object value) {
        this.orWithBracket(fieldName, "=", value);
        return this;
    }

    /**
     * 添加 括号 的 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria orWithBracket(String fieldName, String fieldOperator, Object... values) {
        this.sqlBuilder.add("or ( ", fieldName, fieldOperator, FieldType.WHERE, values);
        return this;
    }

    /**
     * 添加 where 条件中的结束 括号
     *
     * @return
     */
    public Criteria end() {
        this.sqlBuilder.add(null, null, null, FieldType.BRACKET_END);
        return this;
    }

    /**
     * 添加升序排列属性
     *
     * @param fieldNames
     * @return
     */
    public Criteria asc(String... fieldNames) {
        for (String fieldName : fieldNames) {
            this.sqlBuilder.add(null, fieldName, null, FieldType.ORDER_BY_ASC);
        }
        return this;
    }

    /**
     * 添加降序排列属性
     *
     * @param fieldNames
     * @return
     */
    public Criteria desc(String... fieldNames) {
        for (String fieldName : fieldNames) {
            this.sqlBuilder.add(null, fieldName, null, FieldType.ORDER_BY_DESC);
        }
        return this;
    }

    public SqlResult build() {
        return this.sqlBuilder.build();
    }

    /**
     * 初始化实体信息
     *
     * @param entity
     * @param isIgnoreNull
     * @return
     */
    public Criteria initEntity(Object entity, Boolean isIgnoreNull) {
        this.sqlBuilder.initEntity(entity, isIgnoreNull);
        return this;
    }

    public String getPkName() {
        return this.sqlBuilder.getTableInfo().getPkField();
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }

}
