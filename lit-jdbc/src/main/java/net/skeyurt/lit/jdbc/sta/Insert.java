package net.skeyurt.lit.jdbc.sta;

/**
 * User : liulu
 * Date : 2017/6/4 16:38
 * version $Id: Insert.java, v 0.1 Exp $
 */
public interface Insert extends Statement {

    /**
     * Insert 语句的 字段名
     *
     * @param fieldNames 字段名
     * @return Insert
     */
    Insert field(String... fieldNames);

    /**
     * Insert 语句的 value值
     *
     * @param values 值
     * @return Insert
     */
    Insert values(Object... values);

    /**
     * 将值直接拼到 Insert 语句中，不采用 ? 占位符的方式
     *
     * @param values 值
     * @return Insert
     */
    Insert nativeValues(String... values);

    /**
     * 初始化 inset，将entity 中不为空的属性添加到 insert 的字段中
     *
     * @param entity 实体
     * @return Insert
     */
    Insert initEntity(Object entity);

    /**
     * @return 执行 insert 后的 id 值
     */
    Object execute();

}
