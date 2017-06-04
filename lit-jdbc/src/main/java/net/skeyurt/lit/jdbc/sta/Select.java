package net.skeyurt.lit.jdbc.sta;

import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.Pager;

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

    Select<T> addField(String... fieldNames);

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
     * 查询列表
     *
     * @return 实体列表
     */
    List<T> list();

    PageList<T> pageList(Pager pager);

    PageList<T> pageList(int pageSize, int pageNum);

    /**
     * 分页查询列表
     *
     * @param pageSize   每页多少条
     * @param pageNum    第几页
     * @param queryCount 是否查 count
     * @return
     */
    PageList<T> pageList(int pageSize, int pageNum, boolean queryCount);

    /**
     * 查询指定类型
     *
     * @param clazz 指定类型的 class
     * @param <E>   对象类型
     * @return 制定的类型
     */
    <E> E objResult(Class<E> clazz);

}
