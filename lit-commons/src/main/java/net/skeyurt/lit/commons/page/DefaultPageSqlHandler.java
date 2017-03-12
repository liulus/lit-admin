package net.skeyurt.lit.commons.page;

import net.skeyurt.lit.commons.dialect.Dialect;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2017-2-17 22:39
 * version $Id: DefaultPageSqlHandler.java, v 0.1 Exp $
 */
public class DefaultPageSqlHandler implements PageSqlHandler {

    private Dialect dialect;

    private static final String COUNT = "select count(*) ";

    @Override
    public String getPageSql(String sql, String dbName, int pageSize, int pageNum) {

        return getDialect(dbName).getPageSql(sql, pageSize, pageNum);
    }


    @Override
    public String getCountSql(String sql, String dbName) {
        if (StringUtils.isEmpty(sql)) {
            return "";
        }
        sql = sql.toLowerCase().replaceAll(" {2,}+", " ");

        if (sql.contains("order by")) {
            if (sql.lastIndexOf('?') > sql.indexOf("order by")) {
                return COUNT + sql.substring(sql.indexOf("from"));
            }
            return COUNT + sql.substring(sql.indexOf("from"), sql.indexOf("order by"));
        }
        return COUNT + sql.substring(sql.indexOf("from"));
    }

    public Dialect getDialect(String dbName) {
        return dialect == null ? Dialect.valueOf(dbName) : dialect;
    }


}
