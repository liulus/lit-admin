package com.lit.dao.builder;

import java.util.Collections;
import java.util.List;

/**
 * User : liulu
 * Date : 2016-11-23 20:11
 * version $Id: SqlResult.java, v 0.1 Exp $
 */
public class SqlResult {

    public static final SqlResult EMPTY_RESULT = new SqlResult("", Collections.emptyList());

    private String sql;

    private List<Object> params;

    public SqlResult() {
    }

    public SqlResult(String sql, List<Object> params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
