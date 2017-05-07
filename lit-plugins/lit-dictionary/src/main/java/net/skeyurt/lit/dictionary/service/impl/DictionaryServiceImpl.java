package net.skeyurt.lit.dictionary.service.impl;

import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import net.skeyurt.lit.dictionary.service.DictionaryService;
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
    private JdbcDao jdbcDao;

    @Override
    public List<Dictionary> queryPageList(DictionaryQo qo) {
        // 查询根级 字典数据
        qo.setDictLevel(1);
        return jdbcDao.queryPageList(Dictionary.class, qo);
    }

    @Override
    public void add(Dictionary dictionary) {

    }

    @Override
    public void update(Dictionary dictionary) {

    }
}
