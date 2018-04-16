package com.github.lit.menu.dao;

import com.github.lit.menu.model.Menu;
import com.github.lit.plugin.dao.BaseDao;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/1 15:58
 * version $Id: MenuDao.java, v 0.1 Exp $
 */
public interface MenuDao extends BaseDao<Menu> {

    Menu findByCodeAndParentId(String code, Long parentId);

    void move(Long parentId, Long[] ids);

    List<Menu> findAll();

    List<Menu> findByAuthorities(List<String> authorities);
}
