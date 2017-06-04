package net.skeyurt.lit.jdbc.sta;

/**
 * User : liulu
 * Date : 2017/6/4 17:00
 * version $Id: Update.java, v 0.1 Exp $
 */
public interface Update extends Condition<Update> {

    /**
     * createUpdate 语句中的 set 字段
     *
     * @param fieldNames
     * @return
     */
    Update set(String... fieldNames);

    /**
     * set 字段对应 的值
     *
     * @param values
     * @return
     */
    Update values(Object... values);

    Update initEntity(Object entity, boolean isIgnoreNull);

    int execute();
}
