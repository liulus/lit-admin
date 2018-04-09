package com.github.lit.dictionary.dao.impl;

import com.github.lit.dictionary.dao.DictionaryDao;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.dao.AbstractBaseDao;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/3/17 20:03
 * version $Id: DictionaryDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class DictionaryDaoImpl extends AbstractBaseDao<Dictionary, DictionaryQo> implements DictionaryDao {

    @Override
    protected void buildCondition(Select<Dictionary> select, DictionaryQo qo) {
        select.where(Dictionary::getParentId).equalsTo(qo.getParentId());

        if (!Strings.isNullOrEmpty(qo.getKeyword())) {
            select.and()
                    .bracket(Dictionary::getDictKey).like(qo.getKeyword())
                    .or(Dictionary::getDictValue).like(qo.getKeyword())
                    .or(Dictionary::getMemo).like(qo.getKeyword())
                    .end();
        }

        if (!Strings.isNullOrEmpty(qo.getDictKey())) {
            select.and(Dictionary::getDictKey).equalsTo(qo.getDictKey());
        }

        select.asc(Dictionary::getOrderNum);
    }

    @Override
    public Dictionary findByKeyAndParentId(String dictKey, Long parentId) {
        return getSelect()
                .where(Dictionary::getParentId).equalsTo(parentId)
                .and(Dictionary::getDictKey).equalsTo(dictKey)
                .single();
    }

    @Override
    public List<Dictionary> findByIds(Long[] ids) {
        return getSelect()
                .where(Dictionary::getDictId).in((Object[]) ids)
                .list();
    }

    @Override
    public Integer countByParentId(Long parentId) {
        return getSelect().where(Dictionary::getParentId).equalsTo(parentId).count();
    }

}
