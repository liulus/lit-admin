package com.github.lit.dictionary.dao.impl;

import com.github.lit.dictionary.dao.DictionaryDao;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.dao.AbstractBaseDao;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

/**
 * User : liulu
 * Date : 2018/3/17 20:03
 * version $Id: DictionaryDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class DictionaryDaoImpl extends AbstractBaseDao<Dictionary, DictionaryQo> implements DictionaryDao {

    private static final String PARENT_ID = "parentId";

    @Override
    protected void buildCondition(Select<Dictionary> select, DictionaryQo qo) {
        select.where(PARENT_ID).equalsTo(qo.getParentId());

        if (!Strings.isNullOrEmpty(qo.getKeyword())) {
            select.and()
                    .bracket("dictKey").like(qo.getKeyword())
                    .or("dictValue").like(qo.getKeyword())
                    .or("memo").like(qo.getKeyword())
                    .end();
        }

        if (!Strings.isNullOrEmpty(qo.getDictKey())) {
            select.and("dictKey").equalsTo(qo.getDictKey());
        }

        if (qo.getOrder()) {
            select.asc("orderNum");
        }
    }

    @Override
    public Dictionary findByKeyAndParentId(String dictKey, Long parentId) {
        return getSelect()
                .where(PARENT_ID).equalsTo(parentId)
                .and("dictKey").equalsTo(dictKey)
                .single();
    }

    @Override
    public Integer findMaxOrder(Long parentId) {
        return getSelect()
                .function("max", "orderNum")
                .where(PARENT_ID).equalsTo(parentId)
                .single(Integer.class);
    }

    @Override
    public Integer countByParentId(Long parentId) {
        return getSelect().where(PARENT_ID).equalsTo(parentId).count();
    }

}
