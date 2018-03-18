package com.github.lit.dictionary.dao.impl;

import com.github.lit.dictionary.dao.DictionaryDao;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.statement.select.Select;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2018/3/17 20:03
 * version $Id: DictionaryDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class DictionaryDaoImpl implements DictionaryDao {

    @Resource
    private JdbcTools jdbcTools;

    @Override
    public List<Dictionary> findPageList(DictionaryQo qo) {
        return buildSelect(qo).page(qo).list();
    }

    @Override
    public Dictionary findByKeyAndParentId(String dictKey, Long parentId) {
        DictionaryQo qo = DictionaryQo.builder().dictKey(dictKey).parentId(parentId).build();
        return buildSelect(qo).single();
    }

    @Override
    public Integer findMaxOrder(Long parentId) {
        return jdbcTools.select(Dictionary.class)
                .function("max", "orderNum")
                .where("parentId").equalsTo(parentId)
                .single(Integer.class);
    }

    @Override
    public Select<Dictionary> buildSelect(DictionaryQo qo) {
        Select<Dictionary> select = jdbcTools.select(Dictionary.class)
                .where("parentId").equalsTo(qo.getParentId());

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

        if (qo.getSystem() != null) {
            select.and("system").equalsTo(qo.getSystem());
        }

        select.asc("orderNum");

        return select;
    }
}
