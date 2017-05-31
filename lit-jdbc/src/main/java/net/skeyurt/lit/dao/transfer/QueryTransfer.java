package net.skeyurt.lit.dao.transfer;

import net.skeyurt.lit.dao.builder.SqlSelect;

/**
 * User : liulu
 * Date : 2016-10-5 12:52
 */
public interface QueryTransfer<Qo> {

    void transQuery(Qo qo, SqlSelect selects, Class<?> entityClass);

}
