package net.skeyurt.lit.jdbc.sta;

/**
 * User : liulu
 * Date : 2017/6/4 16:38
 * version $Id: Insert.java, v 0.1 Exp $
 */
public interface Insert extends Statement {

    /**
     * createInsert 语句的 字段名
     *
     * @param fieldNames
     * @return
     */
    Insert field(String... fieldNames);

    /**
     * createInsert 语句的 value值
     *
     * @param values
     * @return
     */
    Insert values(Object... values);

    /**
     * 将值直接拼到 createInsert 语句中，不采用 ? 占位符的方式
     *
     * @param values
     * @return
     */
    Insert nativeValues(String... values);

    Insert initEntity(Object entity);

    Object execute();

}
