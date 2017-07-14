package net.skeyurt.lit.dictionary.dao.impl;

import net.skeyurt.lit.dictionary.dao.DictionaryDao;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.jdbc.JdbcTools;
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
    private JdbcTools jdbcTools;

    @Override
    public int queryMaxOrder(Long parentId) {
        Integer maxOrder = jdbcTools.createSelect(Dictionary.class)
                .addFunc("max", "orderNum")
                .where("parentId", parentId).single(int.class);
        return maxOrder == null ? 0 : maxOrder;
    }
}
