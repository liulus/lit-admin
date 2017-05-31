package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.model.SqlResult;

/**
 * User : liulu
 * Date : 2016-11-23 20:26
 * version $Id: SqlBuilder.java, v 0.1 Exp $
 */
public interface SqlBuilder {

    /**
     * 构建 sql 结果，包括 sql 语句和参数值
     *
     * @return SqlResult
     */
    SqlResult build();

}
