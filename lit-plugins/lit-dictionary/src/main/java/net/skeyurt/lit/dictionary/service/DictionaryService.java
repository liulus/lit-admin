package net.skeyurt.lit.dictionary.service;

import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.vo.DictionaryVo;

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
    List<Dictionary> queryPageList(DictionaryVo qo);

    /**
     * 添加字典对象
     *
     * @param dictionary
     */
    void insert(Dictionary dictionary);

    /**
     * 更新字典对象
     *
     * @param dictionary
     */
    void update(Dictionary dictionary);


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


    /**
     * 根据 根字典key 查询字典
     *
     * @param key
     * @return
     */
    Dictionary findByRootKey(String key);


    /**
     * 根据字典key 查询字典
     *
     * @param keys
     * @return
     */
    Dictionary findByKeys(String... keys);

    /**
     * 查找根级字典的所有子字典
     *
     * @param rootKey
     * @return
     */
    List<Dictionary> findChildByRootKey(String rootKey);

    /**
     * 查找指定字典下的子字典
     *
     * @param keys
     * @return
     */
    List<Dictionary> findChildByKeys(String... keys);

    /**
     * 根据父字典Id查询所有子字典
     *
     * @param parentId
     * @return
     */
    List findChildByParentId(Long parentId);
}
