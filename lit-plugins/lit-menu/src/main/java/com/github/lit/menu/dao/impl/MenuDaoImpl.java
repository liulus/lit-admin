package com.github.lit.menu.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.menu.dao.MenuDao;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.plugin.core.dao.AbstractBaseDao;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/1 15:58
 * version $Id: MenuDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class MenuDaoImpl extends AbstractBaseDao<Menu> implements MenuDao {


    @Override
    protected void buildCondition(Select<Menu> select, Object obj) {

        MenuQo menuQo = (MenuQo) obj;

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
    public void move(Long parentId, Long[] ids) {
        jdbcTools.createUpdate(Menu.class)
                .set(Menu::getParentId, parentId)
                .where(Menu::getId).in((Object[]) ids)
                .execute();
    }

    @Override
    public List<Menu> findAll() {
        return getSelect().asc(Menu::getOrderNum).list();
    }

    @Override
    public List<Menu> findByAuthorities(List<String> authorities) {
        Select<Menu> select = getSelect().where(Menu::getAuthCode).equalsTo("");
        if (!CollectionUtils.isEmpty(authorities)) {
            select.or(Menu::getAuthCode).in(authorities.toArray());
        }
        return select.list();
    }
}
