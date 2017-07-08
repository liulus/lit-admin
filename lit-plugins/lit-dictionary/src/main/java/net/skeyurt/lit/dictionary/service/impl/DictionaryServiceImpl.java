package net.skeyurt.lit.dictionary.service.impl;

import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.dictionary.dao.DictionaryDao;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import net.skeyurt.lit.dictionary.service.DictionaryService;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/4/8 20:49
 * version $Id: DictionaryServiceImpl.java, v 0.1 Exp $
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private JdbcTools jdbcTools;

    @Resource
    private DictionaryDao dictionaryDao;

    @Override
    public List<Dictionary> queryPageList(DictionaryQo qo) {

        Select<Dictionary> select = jdbcTools.createSelect(Dictionary.class);
        processSelect(qo, select);

        return select.page(qo).list();
    }

    private void processSelect(DictionaryQo qo, Select<Dictionary> select) {
        select.where("parentId", qo.getParentId());

        if (StringUtils.isNoneEmpty(qo.getKeyWord())) {
            select.andWithBracket("dictKey", Logic.LIKE, qo.getBlurKeyWord())
                    .or("dictValue", Logic.LIKE, qo.getBlurKeyWord())
                    .or("memo", Logic.LIKE, qo.getBlurKeyWord())
                    .end();
        }

        if (qo.getSystem() != null) {
            select.and("system", qo.getSystem());
        }

    }

    @Override
    public void add(Dictionary dictionary) {

        DictionaryQo qo = new DictionaryQo();
        qo.setDictKey(dictionary.getDictKey());
        qo.setParentId(dictionary.getParentId());

        Dictionary dict = jdbcTools.queryForSingle(Dictionary.class, qo);
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

        int maxOrder = dictionaryDao.queryMaxOrder(dictionary.getParentId());
        dictionary.setOrderNum(maxOrder + 1);
        dictionary.setDictId((Long) jdbcTools.insert(dictionary));
    }

    @Override
    public void update(Dictionary dictionary) {
        jdbcTools.update(dictionary);
    }

    @Override
    public Dictionary findRootByKey(String key) {
        return jdbcTools.createSelect(Dictionary.class).where("dictKey", key).and("parentId", null).single();
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
}
