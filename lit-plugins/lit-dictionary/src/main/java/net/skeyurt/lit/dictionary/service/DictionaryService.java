package net.skeyurt.lit.dictionary.service;

import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/4/8 20:47
 * version $Id: DictionaryService.java, v 0.1 Exp $
 */

public interface DictionaryService {

    /**
     * 根据查询对象查询 字典列表
     *
     * @param qo 查询对象
     * @return
     */
    List<Dictionary> queryPageList(DictionaryQo qo);

    /**
     * 添加字典对象
     *
     * @param dictionary
     */
    void add(Dictionary dictionary);

    /**
     * 更新字典对象
     *
     * @param dictionary
     */
    void update(Dictionary dictionary);

    Dictionary findRootByKey(String key);

    /**
     * 根据 Id 查询字典对象
     *
     * @param dictId Id
     * @return
     */
    Dictionary findById(Long dictId);

    /**
     * 根据 Id 删除字典对象
     *
     * @param ids Id
     * @return
     */
    void delete(Long... ids);

//    Dictionary findByKeys(String... keys);


}
