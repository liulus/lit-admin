package net.skeyurt.lit.dao.enums;

/**
 * 拼接sql 时添加的字段的操作类型
 * User : liulu
 * Date : 2016-12-8 20:19
 * version $Id: FieldType.java, v 0.1 Exp $
 */
public enum FieldType {

    NORMAL,

    INSERT,

    UPDATE,

    WHERE,

    INCLUDE,

    EXCLUDE,

    ORDER_BY_ASC,

    ORDER_BY_DESC,

    BRACKET_BEGIN,

    BRACKET_END,

    /** 函数 */
    FUNC,

    /** 拼装sql时忽略 */
    TRANSIENT
}
