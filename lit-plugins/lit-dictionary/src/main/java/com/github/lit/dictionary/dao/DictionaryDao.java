package com.github.lit.dictionary.dao;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.plugin.dao.BaseDao;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/3/17 20:02
 * version $Id: DictionaryDao.java, v 0.1 Exp $
 */
public interface DictionaryDao extends BaseDao<Dictionary, DictionaryQo> {

    Dictionary findByKeyAndParentId(String dictKey, Long parentId);

    Integer countByParentId(Long parentId);

    List<Dictionary> findByIds(Long[] ids);
}
