package com.github.lit.dictionary.dao;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.jdbc.statement.select.Select;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/3/17 20:02
 * version $Id: DictionaryDao.java, v 0.1 Exp $
 */
public interface DictionaryDao {

    Select<Dictionary> buildSelect(DictionaryQo qo);

    List<Dictionary> findPageList(DictionaryQo qo);

    Dictionary findByKeyAndParentId(String dictKey, Long parentId);

    Integer findMaxOrder(Long parentId);
}
