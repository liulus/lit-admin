package com.github.lit.dictionary.service.impl;

import com.github.lit.commons.exception.BizException;
import com.github.lit.dictionary.dao.DictionaryDao;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.plugin.exception.AppException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/4/8 20:49
 * version $Id: DictionaryServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryDao dictionaryDao;

    @Override
    public Long insert(Dictionary dict) {

        checkDictKey(dict.getDictKey(), dict.getParentId());

        // todo 设置层级索引

        dict.setSystem(false);

        Integer maxOrder = dictionaryDao.findMaxOrder(dict.getParentId());
        dict.setOrderNum(maxOrder == null ? 1 : maxOrder + 1);

        return dictionaryDao.insert(dict);
    }

    @Override
    public int update(Dictionary dictionary) {
        Dictionary oldDict = findById(dictionary.getDictId());

        if (Objects.equals(dictionary.getDictKey(), oldDict.getDictKey())) {
            return dictionaryDao.update(dictionary);
        }
        checkDictKey(dictionary.getDictKey(), dictionary.getParentId());
        return dictionaryDao.update(dictionary);
    }

    private void checkDictKey(String dictKey, Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        Dictionary dict = dictionaryDao.findByKeyAndParentId(dictKey, parentId);
        if (dict != null) {
            throw new BizException("字典Key已经存在!");
        }
    }

    @Override
    public Dictionary findById(Long dictId) {
        return dictionaryDao.findById(dictId);
    }

    @Override
    public List<Dictionary> findPageList(DictionaryQo qo) {
        return dictionaryDao.findPageList(qo);
    }

    @Override
    public int deleteByIds(Long... ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        List<Long> validIds = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Dictionary dictionary = findById(id);
            if (dictionary == null) {
                continue;
            }
            if (dictionary.getSystem()) {
                throw new AppException(String.format("%s 是系统级字典, 不允许删除 !", dictionary.getDictKey()));
            }
            int childDict = dictionaryDao.countByParentId(id);
            if (childDict > 0) {
                throw new AppException(String.format("请先删除 %s 的子字典数据 !", dictionary.getDictKey()));
            }
            validIds.add(id);
        }
        return dictionaryDao.deleteByIds(validIds.toArray(new Long[validIds.size()]));
    }


    /**
     * *********************************************** *
     * ****************  以下为接口用  **************** *
     * *********************************************** *
     */


    @Override
    public Dictionary findByRootKey(String key) {
        DictionaryQo qo = DictionaryQo.builder().dictKey(key).build();
        return dictionaryDao.findSingle(qo);
    }

    @Override
    public Dictionary findByKeys(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        Dictionary result = new Dictionary();

        for (String key : keys) {
            result = dictionaryDao.findByKeyAndParentId(key, result.getDictId());
        }
        return result;
    }

    @Override
    public List<Dictionary> findChildByRootKey(String rootKey) {

        Dictionary rootDict = findByRootKey(rootKey);
        if (rootDict == null) {
            return Collections.emptyList();
        }

        return findChildByParentId(rootDict.getDictId());
    }

    @Override
    public List<Dictionary> findChildByKeys(String... keys) {

        Dictionary parentDict = findByKeys(keys);
        if (parentDict == null) {
            return Collections.emptyList();
        }
        return findChildByParentId(parentDict.getDictId());
    }

    @Override
    public List<Dictionary> findChildByParentId(Long parentId) {
        DictionaryQo qo = DictionaryQo.builder().parentId(parentId).build();
        return dictionaryDao.findList(qo);
    }


}
