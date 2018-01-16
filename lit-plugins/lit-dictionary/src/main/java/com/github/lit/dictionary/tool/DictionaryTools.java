package com.github.lit.dictionary.tool;

import com.github.lit.commons.context.SpringContextUtils;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.service.DictionaryService;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/14 14:12
 * version $Id: DictionaryTools.java, v 0.1 Exp $
 */
public class DictionaryTools {

    private static final DictionaryService DICT_SERVICE = SpringContextUtils.getBean(DictionaryService.class);

    /**
     * 根据 根字典key 查询字典
     *
     * @param rootKey 根字典key
     * @return
     */
    public static Dictionary findByRootKey(String rootKey) {
        return DICT_SERVICE.findByRootKey(rootKey);
    }

    /**
     * 根据字典的key查询字典
     *
     * @param keys
     * @return
     */
    public static Dictionary findByKeys(String... keys) {
        return DICT_SERVICE.findByKeys(keys);
    }

    /**
     * 查询根级字典的所有子字典
     *
     * @param rootKey
     * @return
     */
    public static List<Dictionary> findChildByRootKey(String rootKey) {
        return DICT_SERVICE.findChildByRootKey(rootKey);
    }

    /**
     * 查询指定字典下的所有子字典
     *
     * @param keys
     * @return
     */
    public static List<Dictionary> findChildByKeys(String... keys) {
        return DICT_SERVICE.findChildByKeys(keys);
    }
}
