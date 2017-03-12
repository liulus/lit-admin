package net.skeyurt.lit.dao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * User : liulu
 * Date : 2016-11-23 20:11
 * version $Id: SqlResult.java, v 0.1 Exp $
 */
@Getter
@Setter
@NoArgsConstructor
public class SqlResult {

    public static final SqlResult EMPTY_RESULT = new SqlResult("", Collections.emptyList());

    private String sql;

    private List<Object> params;

    public SqlResult(String sql, List<Object> params) {
        this.sql = sql;
        this.params = params;
    }

}
