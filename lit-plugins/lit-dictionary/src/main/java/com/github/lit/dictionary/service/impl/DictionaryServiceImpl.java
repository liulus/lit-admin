package com.github.lit.dictionary.service.impl;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.model.DictionaryVo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.PageResult;
import com.github.lit.support.sql.SQL;
import com.github.lit.support.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
        SQL sql = SQL.baseSelect(Dictionary.class)
                .WHERE("parent_id = :parentId")
                .WHERE("dict_key = :dictKey");
        Map<String, Object> params = new HashMap<>(2);
        params.put("parentId", parentId);
        params.put("dictKey", dictKey);
        return jdbcRepository.selectForObject(sql, params, Dictionary.class);
    }

    @Override
    public Dictionary findById(Long id) {
        if (id == null || id == 0L) {
            return null;
        }
        return jdbcRepository.selectById(Dictionary.class, id);
    }

    @Override
    public PageResult<Dictionary> findPageList(DictionaryQo qo) {
        SQL sql = SQL.baseSelect(Dictionary.class);
        if (qo.getParentId() != null) {
            sql.WHERE("parent_id = :parentId");
        }
        if (StringUtils.hasText(qo.getKeyword())) {
            qo.setKeyword("%" + qo.getKeyword() + "%");
            sql.WHERE("(dict_key like :keyword or dict_value like :keyword or remark like :keyword)");
        }
        sql.ORDER_BY("order_num");
        return jdbcRepository.selectForPageList(sql, qo, Dictionary.class);
    }

    @Override
    public DictionaryVo.Detail buildDictLevelById(Long id) {
        Dictionary dictionary = findById(id);
        if (dictionary == null) {
            return null;
        }
        DictionaryVo.Detail result = BeanUtils.convert(dictionary, new DictionaryVo.Detail());
        buildLevel(Collections.singletonList(result));
        return result;
    }


    private void buildLevel(List<DictionaryVo.Detail> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        List<Long> ids = result.stream().map(DictionaryVo.Detail::getId).collect(Collectors.toList());
        List<Dictionary> dictionaries = findByParentIds(ids);
        if (CollectionUtils.isEmpty(dictionaries)) {
            return;
        }
        List<DictionaryVo.Detail> details = BeanUtils.convertList(DictionaryVo.Detail.class, dictionaries);
        Map<Long, List<DictionaryVo.Detail>> childDicts = details.stream()
                .sorted(Comparator.comparing(DictionaryVo::getOrderNum))
                .collect(Collectors.groupingBy(DictionaryVo.Detail::getParentId));

        for (DictionaryVo.Detail detail : result) {
            detail.setChildren(childDicts.get(detail.getId()));
        }
        buildLevel(details);
    }

    private List<Dictionary> findByParentIds(Collection<Long> parentIds) {

        SQL sql = SQL.baseSelect(Dictionary.class)
                .WHERE("parent_id in (:parentIds)");

        return jdbcRepository.selectForList(sql, Collections.singletonMap("parentIds", parentIds), Dictionary.class);
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
