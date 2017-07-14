package net.skeyurt.lit.dictionary.service.impl;

import com.google.common.base.Strings;
import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.dictionary.dao.DictionaryDao;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import net.skeyurt.lit.dictionary.service.DictionaryService;
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

/**
 * User : liulu
 * Date : 2017/4/8 20:49
 * version $Id: DictionaryServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private JdbcTools jdbcTools;

    @Resource
    private DictionaryDao dictionaryDao;

    @Override
    public List<Dictionary> queryPageList(DictionaryQo qo) {

        Select<Dictionary> select = buildSelect(qo);

        return select.page(qo).list();
    }

    private Select<Dictionary> buildSelect(DictionaryQo qo) {
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

        return select;
    }

    @Override
    public void add(Dictionary dictionary) {

        checkDictKey(dictionary.getDictKey(), dictionary.getParentId());

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

        int maxOrder = dictionaryDao.queryMaxOrder(dictionary.getParentId());
        dictionary.setOrderNum(maxOrder + 1);
        dictionary.setDictId((Long) jdbcTools.insert(dictionary));
    }

    @Override
    public void update(Dictionary dictionary) {
        checkDictKey(dictionary.getDictKey(), dictionary.getParentId());
        jdbcTools.update(dictionary);
    }

    private void checkDictKey(String dictKey, Long parentId) {
        Dictionary dict = findByKeyAndParentId(dictKey, parentId);
        if (dict != null) {
            throw new AppCheckedException("字典Key已经存在!");
        }
    }

    private Dictionary findByKeyAndParentId(String dictKey, Long parentId) {

        DictionaryQo qo = DictionaryQo.builder()
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


    @Override
    public Dictionary findByRootKey(String key) {
        DictionaryQo qo = DictionaryQo.builder().dictKey(key).build();
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

        DictionaryQo qo = DictionaryQo.builder().parentId(rootDict.getDictId()).build();

        return buildSelect(qo).list();
    }

    @Override
    public List<Dictionary> findChildByKeys(String... keys) {

        Dictionary parentDict = findByKeys(keys);
        if (parentDict == null) {
            return Collections.emptyList();
        }

        DictionaryQo qo = DictionaryQo.builder().parentId(parentDict.getDictId()).build();

        return buildSelect(qo).list();
    }

    @Override
    public List<Dictionary> findChildByParentId(Long parentId) {
        Dictionary parentDict = findById(parentId);
        if (parentDict == null) {
            return Collections.emptyList();
        }

        DictionaryQo qo = DictionaryQo.builder().parentId(parentDict.getDictId()).build();

        return buildSelect(qo).list();
    }


}
