package net.skeyurt.lit.jdbc;

import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.jdbc.sta.Delete;
import net.skeyurt.lit.jdbc.sta.Insert;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.jdbc.sta.Update;

import java.io.Serializable;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/6/4 11:13
 * version $Id: JdbcTools.java, v 0.1 Exp $
 */
public interface JdbcTools {

    <T> Object insert(T t);

    <T> int delete(T t);

    <T> int deleteByIds(Class<T> clazz, Serializable... ids);

    <T> int update(T t);

    <T> int update(T t, boolean isIgnoreNull);

    <T> T get(Class<T> clazz, Serializable id);

    <T> T findByProperty(Class<T> clazz, String propertyName, Object propertyValue);

    <T, Qo> T queryForSingle(Class<T> clazz, Qo qo);

    <T, Qo> List<T> query(Class<T> clazz, Qo qo);

    <T, Qo extends Pager> PageList<T> queryPageList(Class<T> clazz, Qo qo);

    <T, Qo> int count(Class<T> clazz, Qo qo);



    Insert createInsert(Class<?> clazz);

    Delete createDelete(Class<?> clazz);

    Update createUpdate(Class<?> clazz);

    <T> Select<T> createSelect(Class<T> clazz);
}
