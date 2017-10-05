package net.skeyurt.lit.dictionary.service.impl;

import com.google.common.base.Strings;
import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.service.DictionaryService;
import net.skeyurt.lit.dictionary.vo.DictionaryVo;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
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
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private JdbcTools jdbcTools;

    @Override
    public List<Dictionary> queryPageList(DictionaryVo vo) {

        Select<Dictionary> select = buildSelect(vo);

        return select.page(vo).list();
    }

    private Select<Dictionary> buildSelect(DictionaryVo qo) {
        Select<Dictionary> select = jdbcTools.createSelect(Dictionary.class).where("parentId", qo.getParentId());

        if (!Strings.isNullOrEmpty(qo.getKeyWord())) {
            select.andWithBracket("dictKey", Logic.LIKE, qo.getBlurKeyWord())
                    .or("dictValue", Logic.LIKE, qo.getBlurKeyWord())
                    .or("memo", Logic.LIKE, qo.getBlurKeyWord())
                    .end();
        }

        if (qo.getSystem() != null) {
            select.and("system", qo.getSystem());
        }

        if (!Strings.isNullOrEmpty(qo.getDictKey())) {
            select.and("dictKey", qo.getDictKey());
        }

        select.asc("orderNum");

        return select;
    }

    @Override
    @Transactional
    public void insert(Dictionary dictionary) {
        Dictionary dict = findByKeyAndParentId(dictionary.getDictKey(), dictionary.getParentId());
        if (dict != null) {
            throw new AppCheckedException("字典Key已经存在!");
        }

        if (dictionary.getParentId() == null) {
            dictionary.setDictLevel(1);
        } else {
            Dictionary parentDict = jdbcTools.get(Dictionary.class, dictionary.getParentId());
            if (parentDict == null) {
                throw new AppCheckedException("父字典信息丢失!");
            }
            dictionary.setDictLevel(parentDict.getDictLevel() + 1);
        }

        if (dictionary.getSystem() == null) {
            dictionary.setSystem(false);
        }

        Integer maxOrder = jdbcTools.createSelect(Dictionary.class)
                .addFunc("max", "orderNum")
                .where("parentId", dictionary.getParentId())
                .single(int.class);

        dictionary.setOrderNum(maxOrder == null ? 1 : maxOrder + 1);
        jdbcTools.insert(dictionary);
    }

    @Override
    @Transactional
    public void update(Dictionary dictionary) {
        Dictionary oldDict = findById(dictionary.getDictId());
        if (!Objects.equals(dictionary.getDictKey(), oldDict.getDictKey())) {
            Dictionary dict = findByKeyAndParentId(dictionary.getDictKey(), dictionary.getParentId());
            if (dict != null) {
                throw new AppCheckedException("字典Key已经存在!");
            }
        }

        jdbcTools.update(dictionary);
    }

    private Dictionary findByKeyAndParentId(String dictKey, Long parentId) {

        DictionaryVo qo = DictionaryVo.builder()
                .dictKey(dictKey)
                .parentId(parentId)
                .build();

        return buildSelect(qo).single();
    }


    @Override
    public Dictionary findById(Long dictId) {
        return jdbcTools.get(Dictionary.class, dictId);
    }

    @Override
    @Transactional
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Long> validIds = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Dictionary dictionary = findById(id);
            if (dictionary == null) {
                continue;
            }
            if (dictionary.getSystem()) {
                throw new AppCheckedException(String.format("%s 是系统级字典, 不允许删除 !", dictionary.getDictKey()));
            }
            int childDict = jdbcTools.createSelect(Dictionary.class)
                    .where("parentId", id)
                    .count();
            if (childDict > 0) {
                throw new AppCheckedException(String.format("请先删除 %s 的子字典数据 !", dictionary.getDictKey()));
            }
            validIds.add(id);
        }
        jdbcTools.deleteByIds(Dictionary.class, validIds.toArray(new Serializable[validIds.size()]));
    }


    /**
     * *********************************************** *
     * ****************  以下为接口用  **************** *
     * *********************************************** *
     */


    @Override
    public Dictionary findByRootKey(String key) {
        DictionaryVo qo = DictionaryVo.builder().dictKey(key).build();
        return buildSelect(qo).single();
    }

    @Override
    public Dictionary findByKeys(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        Dictionary result = new Dictionary();

        for (int i = 0; i < keys.length; i++) {
            result = findByKeyAndParentId(keys[i], result.getParentId());
        }
        return result;
    }

    @Override
    public List<Dictionary> findChildByRootKey(String rootKey) {

        Dictionary rootDict = findByRootKey(rootKey);
        if (rootDict == null) {
            return Collections.emptyList();
        }

        DictionaryVo qo = DictionaryVo.builder().parentId(rootDict.getDictId()).build();

        return buildSelect(qo).list();
    }

    @Override
    public List<Dictionary> findChildByKeys(String... keys) {

        Dictionary parentDict = findByKeys(keys);
        if (parentDict == null) {
            return Collections.emptyList();
        }

        DictionaryVo qo = DictionaryVo.builder().parentId(parentDict.getDictId()).build();

        return buildSelect(qo).list();
    }

    @Override
    public List<Dictionary> findChildByParentId(Long parentId) {
        Dictionary parentDict = findById(parentId);
        if (parentDict == null) {
            return Collections.emptyList();
        }

        DictionaryVo qo = DictionaryVo.builder().parentId(parentDict.getDictId()).build();

        return buildSelect(qo).list();
    }


}
