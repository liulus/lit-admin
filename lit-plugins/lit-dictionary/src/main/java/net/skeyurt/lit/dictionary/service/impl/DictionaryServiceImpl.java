package net.skeyurt.lit.dictionary.service.impl;

import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.dictionary.dao.DictionaryDao;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import net.skeyurt.lit.dictionary.service.DictionaryService;
import net.skeyurt.lit.jdbc.JdbcTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return jdbcTools.queryPageList(Dictionary.class, qo);
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
        jdbcTools.insert(dictionary);
    }

    @Override
    public void update(Dictionary dictionary) {

    }
}
