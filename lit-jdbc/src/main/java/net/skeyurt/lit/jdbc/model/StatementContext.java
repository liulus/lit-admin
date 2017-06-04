package net.skeyurt.lit.jdbc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/6/4 10:21
 * version $Id: StatementContext.java, v 0.1 Exp $
 */
@Getter
@Setter
@NoArgsConstructor
public class StatementContext {

    /**
     * 主键信息
     */
    private String pkColumn;

    /**
     * 主键是否数据库生成
     */
    private boolean generateKeyByDb;

    /**
     * 语句操作类型
     */
    private StatementType statementType;

    /**
     * sql语句
     */
    private String sql;

    /**
     * 语句对应的参数
     */
    private List<Object> params;

    /**
     * 返回的类型
     */
    private Class<?> requireType;

    public StatementContext(String sql, List<Object> params, StatementType type) {
        this.sql = sql;
        this.params = params;
        this.statementType = type;
    }

    public StatementContext(String sql, List<Object> params, StatementType type, Class<?> requireType) {
        this.sql = sql;
        this.params = params;
        this.statementType = type;
        this.requireType = requireType;
    }


    public enum StatementType {
        INSERT,
        DELETE,
        UPDATE,
        SELECT_SINGLE,
        SELECT_LIST,
        SELECT_OBJECT;
    }

}
