package com.lit.service.dictionary.repository;

import com.lit.service.dictionary.model.Dictionary;
import com.lit.support.data.jdbc.JdbcRepository;

import java.util.List;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface DictionaryRepository extends JdbcRepository<Dictionary> {

    Dictionary findByKeyAndParentId(String dictKey, Long parentId);

    List<Dictionary> findByParentIds(List<Long> ids);
}
