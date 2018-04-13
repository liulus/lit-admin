package com.github.lit.plugin.service;

import com.github.lit.commons.page.Page;
import com.github.lit.plugin.dao.BaseDao;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2018-03-18 12:27
 * version $Id: AbstractBaseService.java, v 0.1 Exp $
 */
public class AbstractBaseService<PO, QO extends Page> implements BaseService<PO, QO> {

    @Resource
    private BaseDao<PO> baseDao;

    @Override
    public Long insert(PO po) {
        return baseDao.insert(po);
    }

    @Override
    public int update(PO po) {
        return baseDao.update(po);
    }

    @Override
    public int delete(PO po) {
        return baseDao.delete(po);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return baseDao.deleteByIds(ids);
    }

    @Override
    public PO findById(Long id) {
        return baseDao.findById(id);
    }

    @Override
    public List<PO> findPageList(QO qo) {
        return baseDao.findPageList(qo);
    }

    @Override
    public List<PO> findList(QO qo) {
        return baseDao.findList(qo);
    }

    @Override
    public int count(QO qo) {
        return baseDao.count(qo);
    }
}
