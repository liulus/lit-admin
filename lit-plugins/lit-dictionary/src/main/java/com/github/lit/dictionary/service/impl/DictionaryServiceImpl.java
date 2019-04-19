package com.github.lit.dictionary.service.impl;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.OrderBy;
import com.github.lit.support.page.Page;
import com.github.lit.support.sql.SQL;
import com.github.lit.support.sql.TableMetaDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * User : liulu
 * Date : 2017/4/8 20:49
 * version $Id: DictionaryServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private JdbcRepository jdbcRepository;

    @Override
    public Long insert(Dictionary dict) {

        checkDictKey(dict.getDictKey(), dict.getParentId());
        dict.setSystem(false);
        if (dict.getOrderNum() == null) {
            dict.setOrderNum(0);
        }
        jdbcRepository.insert(dict);
        return dict.getId();
    }

    @Override
    public int update(Dictionary dictionary) {
        Dictionary oldDict = findById(dictionary.getId());
        if (oldDict == null) {
            return 0;
        }
        if (!Objects.equals(dictionary.getDictKey(), oldDict.getDictKey())) {
            if (oldDict.getSystem()) {
                throw new BizException(String.format("%s 是系统级字典, 不允许修改", oldDict.getDictKey()));
            }
            checkDictKey(dictionary.getDictKey(), dictionary.getParentId());
        }
        return jdbcRepository.updateSelective(dictionary);
    }

    private void checkDictKey(String dictKey, Long parentId) {
        Dictionary dict = findByKeyAndParentId(dictKey, parentId);
        if (dict != null) {
            throw new BizException("字典Key已经存在");
        }
    }

    private Dictionary findByKeyAndParentId(String dictKey, Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        TableMetaDate metaDate = TableMetaDate.forClass(Dictionary.class);
        SQL sql = SQL.init().SELECT(metaDate.getAllColumns())
                .FROM(metaDate.getTableName())
                .WHERE("parent_id = :parentId")
                .WHERE("dict_key = :dictKey");
        Map<String, Object> params = new HashMap<>(2);
        params.put("parentId", parentId);
        params.put("dictKey", dictKey);
        return jdbcRepository.selectForObject(sql, params, Dictionary.class);
    }

    @Override
    public Dictionary findById(Long id) {
        return jdbcRepository.selectById(Dictionary.class, id);
    }

    @Override
    public Page<Dictionary> findPageList(DictionaryQo qo) {
        qo.setOrderBy(OrderBy.init().asc(Dictionary::getOrderNum));
        return jdbcRepository.selectPageList(Dictionary.class, qo);
    }

    @Override
    public int deleteByIds(Long... ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        List<Dictionary> dictionaries = jdbcRepository.selectByIds(Dictionary.class, Arrays.asList(ids));
        if (CollectionUtils.isEmpty(dictionaries)) {
            return 0;
        }
        List<Long> validIds = new ArrayList<>(dictionaries.size());
        for (Dictionary dictionary : dictionaries) {
            if (dictionary.getSystem()) {
                throw new BizException(String.format("%s 是系统级字典, 不允许删除 !", dictionary.getDictKey()));
            }
            int childDict = countByParentId(dictionary.getId());
            if (childDict > 0) {
                throw new BizException(String.format("请先删除 %s 的子字典数据 !", dictionary.getDictKey()));
            }
            validIds.add(dictionary.getId());
        }
        return jdbcRepository.deleteByIds(Dictionary.class, validIds);
    }


    /**
     * *********************************************** *
     * ****************  以下为接口用  **************** *
     * *********************************************** *
     */


    @Override
    public Dictionary findByRootKey(String key) {
        return findByKeyAndParentId(key, 0L);
    }

    @Override
    public Dictionary findByKeys(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        Dictionary result = new Dictionary();
        result.setId(0L);

        for (String key : keys) {
            result = findByKeyAndParentId(key, result.getId());
        }
        return result;
    }

    @Override
    public List<Dictionary> findChildByRootKey(String rootKey) {

        Dictionary rootDict = findByRootKey(rootKey);
        if (rootDict == null) {
            return Collections.emptyList();
        }

        return findChildByParentId(rootDict.getId());
    }

    @Override
    public List<Dictionary> findChildByKeys(String... keys) {

        Dictionary parentDict = findByKeys(keys);
        if (parentDict == null) {
            return Collections.emptyList();
        }
        return findChildByParentId(parentDict.getId());
    }

    @Override
    public List<Dictionary> findChildByParentId(Long parentId) {
        return jdbcRepository.selectListByProperty(Dictionary::getParentId, parentId);
    }


    private int countByParentId(Long parentId) {
        return jdbcRepository.countByProperty(Dictionary::getParentId, parentId);
    }

}
