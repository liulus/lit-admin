package net.skeyurt.lit.plugin.dictionary;

/**
 * User : liulu
 * Date : 2017/7/14 11:10
 * version $Id: DictionaryServer.java, v 0.1 Exp $
 */
public interface DictionaryServer {


    /**
     * 根据 根字典key 查询字典
     *
     * @param rootKey 根字典key
     * @return
     */
    DictionaryVo findByRootKey(String rootKey);

    /**
     * 根据字典的key查询字典
     *
     * @param keys
     * @return
     */
    DictionaryVo findByKeys(String... keys);


}
