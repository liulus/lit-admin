package net.skeyurt.lit.dictionary.dao.impl;

import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.builder.Criteria;
import net.skeyurt.lit.dictionary.dao.DictionaryDao;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2017/5/13 18:13
 * version $Id: DictionaryDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class DictionaryDaoImpl implements DictionaryDao {

    @Resource
    private JdbcDao jdbcDao;

    @Override
    public int queryMaxOrder(Long parentId) {
        Criteria criteria = Criteria.select(Dictionary.class).addFunc("max(orderNum)").where("parentId", parentId);
        Integer maxOrder = jdbcDao.queryForObject(criteria, int.class);
        return maxOrder == null ? 0 : maxOrder;
    }
}
