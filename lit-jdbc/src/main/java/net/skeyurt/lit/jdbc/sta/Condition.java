package net.skeyurt.lit.jdbc.sta;

import net.skeyurt.lit.jdbc.enums.Operator;

/**
 * User : liulu
 * Date : 2017/6/4 16:34
 * version $Id: Condition.java, v 0.1 Exp $
 */
interface Condition<T extends Condition<T>> extends Statement {

    T id(Object value);

    T id(Operator operator, Object... values);

    /**
     * 添加 where 条件，默认操作符 =
     *
     * @param fieldName 属性名
     * @param value     值
     * @return
     */
    T where(String fieldName, Object value);

    /**
     * 添加 where 条件
     *
     * @param fieldName
     * @param operator
     * @param values
     * @return
     */
    T where(String fieldName, Operator operator, Object... values);

    /**
     * 添加 and 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    T and(String fieldName, Object value);

    /**
     * 添加 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    T and(String fieldName, Operator operator, Object... values);

    /**
     * 添加带 括号 的 and 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    T andWithBracket(String fieldName, Object value);

    /**
     * 添加 括号 的 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    T andWithBracket(String fieldName, Operator operator, Object... values);

    /**
     * 添加 or 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    T or(String fieldName, Object value);

    /**
     * 添加 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    T or(String fieldName, Operator operator, Object... values);

    /**
     * 添加带 括号 的 or 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    T orWithBracket(String fieldName, Object value);

    /**
     * 添加 括号 的 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    T orWithBracket(String fieldName, Operator operator, Object... values);

    /**
     * 添加 where 条件中的结束 括号
     *
     * @return
     */
    T end();

    T conditionBean(Object bean);


}
