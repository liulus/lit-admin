package net.skeyurt.lit.dao.transfer;

import net.skeyurt.lit.dao.builder.Criteria;

/**
 * User : liulu
 * Date : 2016-10-5 12:52
 */
public interface CriteriaTransfer<Qo> {

    void transQuery(Qo qo, Criteria criteria, Class<?> entityClass);

}
