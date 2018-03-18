package com.github.lit.plugin.dao;

import com.github.lit.commons.page.Page;

import java.util.List;

/**
 * User : liulu
 * Date : 2018-03-18 11:35
 * version $Id: BaseDao.java, v 0.1 Exp $
 */
public interface BaseDao<PO, QO extends Page> {

    Long insert(PO po);

    int update(PO po);

    int delete(PO po);

    int deleteByIds(Long...ids);

    PO findById(Long id);

    PO findByProperty(String property, Object value);

    PO findSingle(QO qo);

    List<PO> findPageList(QO qo);

    List<PO> findList(QO qo);



    int count(QO qo);





}
