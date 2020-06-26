package com.github.lit.service.impl;

import com.github.lit.model.DictionaryQo;
import com.github.lit.model.DictionaryVo;
import com.github.lit.repository.DictionaryRepository;
import com.github.lit.repository.entity.Dictionary;
import com.github.lit.service.DictionaryService;
import com.lit.support.data.domain.Page;
import com.lit.support.data.domain.PageUtils;
import com.lit.support.exception.BizException;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private DictionaryRepository dictionaryRepository;

    @Override
    public Long insert(DictionaryVo.Add add) {
        Dictionary dict = BeanUtils.convert(add, new Dictionary());
        checkDictKey(dict.getDictKey(), dict.getParentId());
        dict.setSystem(false);
        if (dict.getOrderNum() == null) {
            dict.setOrderNum(0);
        }
        dictionaryRepository.insert(dict);
        return dict.getId();
    }

    @Override
    public int update(DictionaryVo.Update update) {
        Dictionary dictionary = BeanUtils.convert(update, new Dictionary());
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
        return dictionaryRepository.updateSelective(dictionary);
    }

    private void checkDictKey(String dictKey, Long parentId) {
        Dictionary dict = dictionaryRepository.findByKeyAndParentId(dictKey, parentId);
        if (dict != null) {
            throw new BizException("字典Key已经存在");
        }
    }

    @Override
    public Dictionary findById(Long id) {
        if (id == null || id == 0L) {
            return null;
        }
        return dictionaryRepository.selectById(id);
    }

    @Override
    public Page<DictionaryVo.Detail> findPageList(DictionaryQo qo) {
        Page<Dictionary> dictionaryPage = dictionaryRepository.selectPageList(qo);
        return PageUtils.convert(dictionaryPage, DictionaryVo.Detail.class);
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
        List<Dictionary> dictionaries = dictionaryRepository.findByParentIds(ids);
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

    @Override
    public int deleteByIds(Long... ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        List<Dictionary> dictionaries = dictionaryRepository.selectByIds(Arrays.asList(ids));
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
        return dictionaryRepository.deleteByIds(validIds);
    }


    /**
     * *********************************************** *
     * ****************  以下为接口用  **************** *
     * *********************************************** *
     */


    @Override
    public Dictionary findByRootKey(String key) {
        return dictionaryRepository.findByKeyAndParentId(key, 0L);
    }

    @Override
    public Dictionary findByKeys(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        Dictionary result = new Dictionary();
        result.setId(0L);

        for (String key : keys) {
            result = dictionaryRepository.findByKeyAndParentId(key, result.getId());
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
        return dictionaryRepository.selectListByProperty(Dictionary::getParentId, parentId);
    }


    private int countByParentId(Long parentId) {
        return dictionaryRepository.countByProperty(Dictionary::getParentId, parentId);
    }

}
