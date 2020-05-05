package com.lit.service.dictionary.service;

import com.lit.service.dictionary.model.Dictionary;
import com.lit.service.dictionary.model.DictionaryQo;
import com.lit.service.dictionary.model.DictionaryVo;
import com.lit.support.data.domain.Page;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/4/8 20:47
 * version $Id: DictionaryService.java, v 0.1 Exp $
 */

public interface DictionaryService {

    /**
     * 添加字典对象
     *
     * @param dictionary Dictionary
     */
    Long insert(Dictionary dictionary);

    /**
     * 更新字典对象
     *
     * @param dictionary Dictionary
     */
    int update(Dictionary dictionary);

    /**
     * 根据 Id 查询字典对象
     *
     * @param id Id
     * @return Dictionary
     */
    Dictionary findById(Long id);

    /**
     * 根据查询对象查询 字典列表
     *
     * @param qo 查询对象
     * @return Dictionary
     */
    Page<Dictionary> findPageList(DictionaryQo qo);

    /**
     * 构建字典的层级
     *
     * @param id id
     * @return DictionaryVo.Detail list
     */
    DictionaryVo.Detail buildDictLevelById(Long id);

    /**
     * 根据 Id 删除字典对象
     *
     * @param ids Id
     * @return Dictionary
     */
    int deleteByIds(Long... ids);

    /**
     * 根据 根字典key 查询字典
     *
     * @param key key
     * @return Dictionary
     */
    Dictionary findByRootKey(String key);


    /**
     * 根据字典key 查询字典
     *
     * @param keys keys
     * @return Dictionary
     */
    Dictionary findByKeys(String... keys);

    /**
     * 查找根级字典的所有子字典
     *
     * @param rootKey rootKey
     * @return List<Dictionary>
     */
    List<Dictionary> findChildByRootKey(String rootKey);

    /**
     * 查找指定字典下的子字典
     *
     * @param keys keys
     * @return List<Dictionary>
     */
    List<Dictionary> findChildByKeys(String... keys);

    /**
     * 根据父字典Id查询所有子字典
     *
     * @param parentId parentId
     * @return
     */
    List<Dictionary> findChildByParentId(Long parentId);
}
