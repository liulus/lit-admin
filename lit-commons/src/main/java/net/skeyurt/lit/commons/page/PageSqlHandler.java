package net.skeyurt.lit.commons.page;

/**
 * User : liulu
 * Date : 2017-2-17 22:34
 * version $Id: PageSqlHandler.java, v 0.1 Exp $
 */
public interface PageSqlHandler {

    /**
     * 获取分页后的sql
     *
     * @param sql      查询 sql
     * @param dbName   数据库名
     * @param pageSize 每页记录数
     * @param pageNum  当前页
     * @return
     */
    String getPageSql(String sql, String dbName, int pageSize, int pageNum);

    /**
     * 获取查询总数的sql
     *
     * @param sql    查询 sql
     * @param dbName 数据库名
     * @return
     */
    String getCountSql(String sql, String dbName);
}
