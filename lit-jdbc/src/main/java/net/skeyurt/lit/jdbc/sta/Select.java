package net.skeyurt.lit.jdbc.sta;

import net.skeyurt.lit.jdbc.enums.JoinType;
import net.skeyurt.lit.jdbc.enums.Operator;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/6/4 16:48
 * version $Id: Select.java, v 0.1 Exp $
 */
public interface Select<T> extends Condition<Select<T>> {

    /**
     * 查询语句中 要查询的属性(不能和 exclude 同时使用)
     *
     * @param fieldNames 字段名
     * @return Select
     */
    Select<T> include(String... fieldNames);

    /**
     * 查询语句中 要排除的属性
     *
     * @param fieldNames 字段名
     * @return Select
     */
    Select<T> exclude(String... fieldNames);

    Select<T> addField(Class<?> tableClass, String... fieldNames);

    Select<T> addFunc(String funcName);

    Select<T> addFunc(String funcName, String... fieldNames);

    Select<T> addFunc(String funcName, boolean distinct, String... fieldNames);

    /**
     * Select 语句中添加函数，目前只支持合计函数（Aggregate functions）暂时不支持 group by 语句
     *
     * @param funcName   函数名，如：max，count
     * @param distinct   是否去重
     * @param allColumns 是否是全部字段
     * @param fieldNames 函数执行的列
     * @return
     */
    Select<T> addFunc(String funcName, boolean distinct, boolean allColumns, String... fieldNames);

    Select<T> alias(String... alias);

    Select<T> tableAlias(String alias);

    <E> Select<T> join(Class<E> tableClass);

    <E> Select<T> simpleJoin(Class<E> tableClass);

    <E> Select<T> join(JoinType joinType, Class<E> tableClass);

    Select<T> on(Class<?> table1, String field1, Operator operator, Class<?> table2, String field2);

    Select<T> joinCondition(Class<?> table1, String field1, Operator operator, Class<?> table2, String field2);

    Select<T> groupBy(String... fields);


    /**
     * 添加升序排列属性
     *
     * @param fieldNames 字段名
     * @return Select
     */
    Select<T> asc(String... fieldNames);

    /**
     * 添加降序排列属性
     *
     * @param fieldNames 字段名
     * @return Select
     */
    Select<T> desc(String... fieldNames);

    /**
     * 查询count
     *
     * @return 记录数
     */
    int count();

    /**
     * 查询单条记录
     *
     * @return 实体
     */
    T single();

    /**
     * 查询单条记录
     *
     * @return 指定类型
     */
    <E> E single(Class<E> clazz);

    /**
     * 查询列表
     *
     * @return 实体列表
     */
    List<T> list();

    /**
     * 查询列表
     *
     * @return 指定类型列表
     */
    <E> List<E> list(Class<E> clazz);

    Select<T> pageNum(int pageNum);

    Select<T> pageSize(int pageSize);

    Select<T> count(boolean queryCount);


}
