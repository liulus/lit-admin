package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.commons.util.ClassUtils;
import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.GenerationType;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.generator.KeyGenerator;
import net.skeyurt.lit.dao.generator.SequenceGenerator;
import net.skeyurt.lit.dao.model.SqlResult;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2016-12-3 17:39
 * version $Id: Criteria.java, v 0.1 Exp $
 */
public class Criteria<T> {

    @Getter
    private Class<T> entityClass;

    private SqlBuilder sqlBuilder;

    private Criteria(Class<T> clazz, SqlBuilder sqlBuilder) {
        this.entityClass = clazz;
        this.sqlBuilder = sqlBuilder;
    }

    /**
     * 初始化 insert 语句
     *
     * @param clazz
     * @return
     */
    public static <T> Criteria<T> insert(Class<T> clazz) {
        return new Criteria<>(clazz, new InsertBuilder(clazz));
    }

    /**
     * 初始化 update 语句
     *
     * @param clazz
     * @return
     */
    public static <T> Criteria<T> update(Class<T> clazz) {
        return new Criteria<>(clazz, new UpdateBuilder(clazz));
    }

    /**
     * 初始化 delete 语句
     *
     * @param clazz
     * @return
     */
    public static <T> Criteria<T> delete(Class<T> clazz) {
        return new Criteria<>(clazz, new DeleteBuilder(clazz));
    }

    /**
     * 初始化 select 语句
     *
     * @param clazz
     * @return
     */
    public static <T> Criteria<T> select(Class<T> clazz) {
        return new Criteria<>(clazz, new SelectBuilder(clazz));
    }

    /**
     * insert into 的属性
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria<T> into(String fieldName, Object value) {
        this.sqlBuilder.add(null, fieldName, null, FieldType.INSERT, value);
        return this;
    }

    /**
     * insert into 的属性
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria<T> into(String fieldName, Object value, boolean isNative) {
        this.sqlBuilder.add(null, fieldName, null, isNative ? FieldType.INSERT_NATIVE : FieldType.INSERT, value);
        return this;
    }

    /**
     * update set 的属性
     *
     * @param fieldName
     * @param value
     * @return
     */
    public Criteria<T> set(String fieldName, Object value) {
        this.sqlBuilder.add(null, fieldName, null, FieldType.UPDATE, value);
        return this;
    }

    /**
     * 查询语句中 要查询的属性(不能和 exclude 同时使用)
     *
     * @param fieldNames
     * @return
     */
    public Criteria<T> include(String... fieldNames) {
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
    public Criteria<T> exclude(String... fieldNames) {
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
    public Criteria<T> addFunc(String func) {
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
    public Criteria<T> where(String fieldName, Object value) {
        this.where(fieldName, Operator.EQ, value);
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
    public Criteria<T> where(String fieldName, Operator fieldOperator, Object... values) {
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
    public Criteria<T> and(String fieldName, Object value) {
        this.and(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria<T> and(String fieldName, Operator fieldOperator, Object... values) {
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
    public Criteria<T> andWithBracket(String fieldName, Object value) {
        this.andWithBracket(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 括号 的 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria<T> andWithBracket(String fieldName, Operator fieldOperator, Object... values) {
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
    public Criteria<T> or(String fieldName, Object value) {
        this.or(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria<T> or(String fieldName, Operator fieldOperator, Object... values) {
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
    public Criteria<T> orWithBracket(String fieldName, Object value) {
        this.orWithBracket(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 括号 的 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public Criteria<T> orWithBracket(String fieldName, Operator fieldOperator, Object... values) {
        this.sqlBuilder.add("or ( ", fieldName, fieldOperator, FieldType.WHERE, values);
        return this;
    }

    /**
     * 添加 where 条件中的结束 括号
     *
     * @return
     */
    public Criteria<T> end() {
        this.sqlBuilder.add(null, null, null, FieldType.BRACKET_END);
        return this;
    }

    /**
     * 添加升序排列属性
     *
     * @param fieldNames
     * @return
     */
    public Criteria<T> asc(String... fieldNames) {
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
    public Criteria<T> desc(String... fieldNames) {
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
    public Criteria<T> initEntity(Object entity, Boolean isIgnoreNull) {
        this.sqlBuilder.initEntity(entity, isIgnoreNull);
        return this;
    }

    public String getPkName() {
        return this.sqlBuilder.getTableInfo().getPkField();
    }

    public String getColumnName(String fieldName) {
        return this.sqlBuilder.getTableInfo().getFieldColumnMap().get(fieldName);
    }

    /**
     * 主键生成器实例的缓存
     */
    private static final Map<String, KeyGenerator> KEY_GENERATOR_CACHE = Collections.synchronizedMap(new HashMap<String, KeyGenerator>());

    private static final SequenceGenerator SEQUENCE_GENERATOR = new SequenceGenerator();

    public KeyGenerator getKeyGenerator() {
        Class<? extends KeyGenerator> generatorClass = this.sqlBuilder.getTableInfo().getGeneratorClass();
        if (generatorClass != null) {
            KeyGenerator keyGenerator = KEY_GENERATOR_CACHE.get(generatorClass.getName());
            if (keyGenerator == null) {
                keyGenerator = ClassUtils.newInstance(generatorClass);
                KEY_GENERATOR_CACHE.put(generatorClass.getName(), keyGenerator);
            }
            if (Objects.equals(generatorClass, SequenceGenerator.class)) {
                SequenceGenerator.setSequenceName(getSequenceName());
            }
            return keyGenerator;
        }
        if (Objects.equals(GenerationType.SEQUENCE, sqlBuilder.getTableInfo().getGenerationType())) {
            SequenceGenerator.setSequenceName(getSequenceName());
            return SEQUENCE_GENERATOR;
        }
        return null;
    }

    public String getSequenceName() {
        return sqlBuilder.getTableInfo().getSequenceName();
    }

    public boolean isAutoGenerateKey() {
        return this.sqlBuilder.getTableInfo().isAutoGenerateKey();
    }
}
