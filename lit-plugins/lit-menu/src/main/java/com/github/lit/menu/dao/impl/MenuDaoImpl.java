package com.github.lit.menu.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.menu.dao.MenuDao;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.plugin.dao.AbstractBaseDao;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/1 15:58
 * version $Id: MenuDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class MenuDaoImpl extends AbstractBaseDao<Menu, MenuQo> implements MenuDao {


    @Override
    protected void buildCondition(Select<Menu> select, MenuQo menuQo) {

        select.where(Menu::getParentId).equalsTo(menuQo.getParentId());

        if (!Strings.isNullOrEmpty(menuQo.getMenuCode())) {
            select.and(Menu::getCode).equalsTo(menuQo.getMenuCode());
        }

        select.asc(Menu::getOrderNum);
    }

    @Override
    public Menu findByCodeAndParentId(String code, Long parentId) {
        return getSelect()
                .where(Menu::getParentId).equalsTo(parentId)
                .and(Menu::getCode).equalsTo(code)
                .single();
    }

    @Override
    public List<Menu> findByIds(Long[] ids) {
        return getSelect()
                .where(Menu::getMenuId).in((Object[]) ids)
                .list();
    }

    @Override
    public void move(Long parentId, Long[] ids) {
        jdbcTools.createUpdate(Menu.class)
                .set(Menu::getParentId, parentId)
                .where(Menu::getMenuId).in((Object[]) ids)
                .execute();
    }

    @Override
    public List<Menu> findAll() {
        return getSelect().list();
    }
}
