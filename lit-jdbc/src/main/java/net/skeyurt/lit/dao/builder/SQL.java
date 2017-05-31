package net.skeyurt.lit.dao.builder;

/**
 * User : liulu
 * Date : 2017/5/29 8:41
 * version $Id: SQL.java, v 0.1 Exp $
 */
public class SQL {

    public static <T> SqlSelect<T> select(Class<T> clazz) {
        return new SqlSelect<>(clazz);
    }

    public static SqlInsert insert(Class<?> clazz) {
        return new SqlInsert(clazz);
    }

    public static SqlUpdate update(Class<?> clazz) {
        return new SqlUpdate(clazz);
    }

    public static SqlDelete delete(Class<?> clazz) {
        return new SqlDelete(clazz);
    }


}
