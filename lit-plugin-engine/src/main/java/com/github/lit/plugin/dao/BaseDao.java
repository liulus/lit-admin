package com.github.lit.plugin.dao;

import com.github.lit.commons.page.Page;
import com.github.lit.jdbc.statement.select.Select;

import java.util.List;

/**
 * User : liulu
 * Date : 2018-03-18 11:35
 * version $Id: BaseDao.java, v 0.1 Exp $
 */
public interface BaseDao<PO> {

    Long insert(PO po);

    int update(PO po);

    int delete(PO po);

    int deleteByIds(Long...ids);

    Select<PO> getSelect();

    PO findById(Long id);

    List<PO> findByIds(Long[] ids);

    PO findByProperty(String property, Object value);

    <QO> PO findSingle(QO qo);

    <QO extends Page> List<PO> findPageList(QO qo);

    <QO> List<PO> findList(QO qo);

    <QO> int count(QO qo);





}
