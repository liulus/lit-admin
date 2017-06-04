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
     * @param fieldNames
     * @return
     */
    Select<T> include(String... fieldNames);

    /**
     * 查询语句中 要排除的属性
     *
     * @param fieldNames
     * @return
     */
    Select<T> exclude(String... fieldNames);

    Select<T> addField(String... fieldNames);

    /**
     * createSelect 语句中添加函数，目前只支持合计函数（Aggregate functions）暂时不支持 group by 语句
     *
     * @param funcName 函数名 如 count, max,
     * @return
     */
    Select<T> addFunc(String funcName);

    Select<T> addFunc(String funcName, String... fieldNames);

    Select<T> addFunc(String funcName, boolean distinct, String... fieldNames);

    Select<T> addFunc(String funcName, boolean distinct, boolean allColumns, String... fieldNames);

    /**
     * 添加升序排列属性
     *
     * @param fieldNames
     * @return
     */
    Select<T> asc(String... fieldNames);

    /**
     * 添加降序排列属性
     *
     * @param fieldNames
     * @return
     */
    Select<T> desc(String... fieldNames);

    int count();

    T single();

    List<T> list();

    PageList<T> pageList(Pager pager);

    PageList<T> pageList(int pageSize, int pageNum);

    PageList<T> pageList(int pageSize, int pageNum, boolean queryCount);

    <E> E objResult(Class<E> clazz);

}
