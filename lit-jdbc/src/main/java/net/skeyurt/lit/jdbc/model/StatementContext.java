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

    private String pkColumn;

    private boolean generateKeyByDb;

    private StatementType statementType;

    private String sql;

    private List<Object> params;

    private Class<?> requireType;

    public StatementContext(String sql, List<Object> params, StatementType type) {
        this.sql = sql;
        this.params = params;
        this.statementType = type;
    }

    public StatementContext(String sql, List<Object> params, StatementType type,Class<?> requireType) {
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
