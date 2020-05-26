package com.lit.service.dictionary.repository.impl;

import com.lit.service.dictionary.model.Dictionary;
import com.lit.service.dictionary.model.DictionaryQo;
import com.lit.service.dictionary.repository.DictionaryRepository;
import com.lit.support.data.SQL;
import com.lit.support.data.domain.Pageable;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class DictionaryRepositoryImpl extends AbstractJdbcRepository<Dictionary> implements DictionaryRepository {

    @Override
    protected SQL buildPageSQL(Pageable condition) {
        DictionaryQo qo = (DictionaryQo) condition;
        SQL sql = SQL.baseSelect(Dictionary.class);
        if (qo.getParentId() != null) {
            sql.WHERE("parent_id = :parentId");
        }
        if (StringUtils.hasText(qo.getKeyword())) {
            qo.setKeyword("%" + qo.getKeyword() + "%");
            sql.WHERE("(dict_key like :keyword or dict_value like :keyword or remark like :keyword)");
        }
        sql.ORDER_BY("order_num");
        return sql;
    }

    @Override
    public Dictionary findByKeyAndParentId(String dictKey, Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        SQL sql = baseSelectSQL()
                .WHERE("parent_id = :parentId")
                .WHERE("dict_key = :dictKey");
        Dictionary params = new Dictionary();
        params.setDictKey(dictKey);
        params.setParentId(parentId);
        return selectSingle(sql, params);
    }

    @Override
    public List<Dictionary> findByParentIds(List<Long> ids) {
        SQL sql = baseSelectSQL().WHERE("parent_id in (:parentIds)");
        return selectList(sql, Collections.singletonMap("parentIds", ids));
    }
}
