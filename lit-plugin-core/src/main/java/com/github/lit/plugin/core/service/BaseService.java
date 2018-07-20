package com.github.lit.plugin.core.service;

import com.github.lit.page.Page;

import java.util.List;

/**
 * User : liulu
 * Date : 2018-03-18 12:20
 * version $Id: BaseService.java, v 0.1 Exp $
 */
public interface BaseService<PO, QO extends Page> {

    Long insert(PO po);

    int update(PO po);

    int delete(PO po);

    int deleteByIds(Long...ids);

    PO findById(Long id);

    List<PO> findPageList(QO qo);

    List<PO> findList(QO qo);

    int count(QO qo);
}
